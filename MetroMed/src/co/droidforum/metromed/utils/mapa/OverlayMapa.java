package co.droidforum.metromed.utils.mapa;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;
import co.droidforum.metromed.R;
import co.droidforum.metromed.activities.adapters.AlimentadoresListAdapter;
import co.droidforum.metromed.activities.adapters.LugaresCercanosListAdapter;
import co.droidforum.metromed.application.AplicationContext;
import co.droidforum.metromed.application.BusinessContext;
import co.droidforum.metromed.bo.EstacionesMetroBO;
import co.droidforum.metromed.bo.FoursquareBO;
import co.droidforum.metromed.dto.EstacionMetroDTO;
import co.droidforum.metromed.dto.FoursquareVenueDTO;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

/**
 * 
 * Clase que extiende la funcionalidad de la clase Overlay de Android Maps para ubicar en el mapa un punto en particular
 * en una capa. La clase apenas se carga, llama al método draw, que es el que sobreescribimos para darle el comportamiento 
 * que queremos.
 * 
 * @author Carlos Daniel Muñoz Idarraga | @cdmunozi
 * @since 30/03/2012
 *
 */

/**
 * Se modifica para que pueda mostrar los lugares cercanos y haga toda la logica de presentacion.
 */
public class OverlayMapa extends Overlay {

	/*
	 * Tendrá en cuenta la posición sobre la que está centrada el mapa en este momento y el nivel de zoom aplicado para convertir 
	 * convenientemente entre latitud-longitud en grados y coordenadas x-y en pixeles
	 */
	private GeoPoint geoPoint;
	private Projection projection;
	private Bitmap iconoGral;
	private ArrayList<FoursquareVenueDTO> mNearbyList;
	EstacionesMetroBO estacionesCercanasBO = BusinessContext.getBean(EstacionesMetroBO.class);
	private ProgressDialog mProgress;
	private MapView mapViewContext;
	private ListView l_v_lugares;
	private LugaresCercanosListAdapter lugaresCercanosListAdapter;
	private EstacionMetroDTO estacionMetroActualDTO;
	
	public OverlayMapa() {
		
	}
	
	public OverlayMapa(GeoPoint geoPoint, Projection projection) {
		this.geoPoint = geoPoint;
		this.projection = projection;
		
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		
		if (shadow == false){
			Point centro = new Point();
			//convierte las coordenadas de latitud y longitud de GeoPoint a coordenadas pixel de x-y
			projection.toPixels(geoPoint, centro);

			//Definimos el pincel de dibujo
			Paint p = new Paint();
			
			//Marca con una imagen apuntador como Google Maps
			Bitmap bm = BitmapFactory.decodeResource(mapView.getResources(),R.drawable.icono_aqui);
			
			canvas.drawBitmap(bm, centro.x - bm.getWidth(), 
					              centro.y - bm.getHeight(), p);
			
			//pinta las estaciones del metro que estan en la BD
			setEstacionesMetro(geoPoint, canvas, mapView);
			
		}
	}
	
	/*
	 * Metodo que a partir de la ubicacion almacenada de cada estacion de metro la pinta en la capa del mapa
	 */
	private void setEstacionesMetro(GeoPoint geoPoint, Canvas canvas, MapView mapView){
		
		EstacionesMetroBO estacionesCercanasBO = BusinessContext.getBean(EstacionesMetroBO.class);
		
		List<EstacionMetroDTO> estacionesMetro = estacionesCercanasBO.getAllEstacionesMetro() ;
		
		for(EstacionMetroDTO estacionMetroDTO : estacionesMetro){
			Double latitud = new Double(estacionMetroDTO.getLatitud());
			Double longitud = new Double(estacionMetroDTO.getLongitud());
			geoPoint = new GeoPoint(new Double(latitud*1E6).intValue() , new Double(longitud*1E6).intValue());
			
			Point centro = new Point();
			//convierte las coordenadas de latitud y longitud de GeoPoint a coordenadas pixel de x-y
			projection.toPixels(geoPoint, centro);

			//Definimos el pincel de dibujo
			Paint p = new Paint();
			
			//Marca con una imagen apuntador como Google Maps dependiendo de la linea
			Bitmap bm = BitmapFactory.decodeResource(mapView.getResources(), getImageEstacion(estacionMetroDTO));
			
			canvas.drawBitmap(bm, centro.x - bm.getWidth(), 
					              centro.y - bm.getHeight(), p);
			
		}
	}
	
	/**
	 * Metodo que retorna la imagen de la estación de acuerdo a la estaciónDTO que se le envie
	 * @author Carlos Granada | @cgranadax
	 * @since 27/04/2011
	 * @param estacionMetroDTO
	 * @return
	 */
	private int getImageEstacion(EstacionMetroDTO estacionMetroDTO){
		int imagenMap = 0;
		if(estacionMetroDTO.getLinea().equalsIgnoreCase(AplicationContext.getValueStringResource(R.string.linea_A_validacion))){
			imagenMap = R.drawable.icono_a_on;
		}else if(estacionMetroDTO.getLinea().equalsIgnoreCase(AplicationContext.getValueStringResource(R.string.linea_B_validacion))){
			imagenMap = R.drawable.icono_b_on;
		}else if(estacionMetroDTO.getLinea().equalsIgnoreCase(AplicationContext.getValueStringResource(R.string.linea_J_validacion))){
			imagenMap = R.drawable.icono_j_on;
		}else if(estacionMetroDTO.getLinea().equalsIgnoreCase(AplicationContext.getValueStringResource(R.string.linea_K_validacion))){
			imagenMap = R.drawable.icono_k_on;
		}else if(estacionMetroDTO.getLinea().equalsIgnoreCase(AplicationContext.getValueStringResource(R.string.linea_L_validacion))){
			imagenMap = R.drawable.icono_l_on;
		}else if(estacionMetroDTO.getLinea().equalsIgnoreCase(AplicationContext.getValueStringResource(R.string.linea_1_validacion))){
			imagenMap = R.drawable.icono_1_on;
		}
		return imagenMap;
	}
	
	@Override
	public boolean onTap(GeoPoint point, MapView mapView) 
	{
		//obtenemos un icono que nos va servir de base para hacer un area invisble de tap
		iconoGral = BitmapFactory.decodeResource(mapView.getResources(),R.drawable.icono_aqui);
		
		//se crea un objeto rectangular invisible que servira como base para ver si hace tap sobre el icono de la estacion
		RectF rectF = new RectF();
		Point screenCoords = new Point();
		//se obtienen las estaciones para asi a cada una crearle el rectangulo invisible sobre el icono
		
		List<EstacionMetroDTO> estacionesMetro = estacionesCercanasBO.getAllEstacionesMetro() ;
		
		for(EstacionMetroDTO estacionMetroDTO : estacionesMetro){
			Double latitud = new Double(estacionMetroDTO.getLatitud());
			Double longitud = new Double(estacionMetroDTO.getLongitud());
			
			//convierte las coordenadas de latitud y longitud de cada estacion a coordenadas pixel de x-y
			mapView.getProjection().toPixels(new GeoPoint(new Double(latitud*1E6).intValue() , new Double(longitud*1E6).intValue()), screenCoords);

			//crea un area rectangulo alrededor del icono para que sirva como testing area y saber si hace tap sobre el icono
			rectF.set(-iconoGral.getWidth()/2,-iconoGral.getHeight(),iconoGral.getWidth()/2,0);
			rectF.offset(screenCoords.x,screenCoords.y);
			
			//obtiene las coordenadas del tap que hace el usuario
			mapView.getProjection().toPixels(point, screenCoords);
			
			//valida si el tap se hizo sobre el icono, en caso afirmativo muestra una alerta con la info de los sitios cercanos
    		if (rectF.contains(screenCoords.x,screenCoords.y)) {
    			//al coincidir con el area levanta la alerta
    			mapViewContext = mapView;
    			
    			//@cgranadax: Se establece una estacion actual generica para acceder al recurso
    			estacionMetroActualDTO = estacionMetroDTO;
    			//@cgranadax: Se cargan los lugares cercanos desde foursquare
    			loadNearbyPlaces(Double.valueOf(latitud), Double.valueOf(longitud));    			
    			break;
    		}
		}
		
		//limpia la seleccion
		point = null;
		
		return true;
	}	
	
	/**
	 * Metodo  que ejecuta un hilo nuevo para consultar los sitios de foursquare, mostrar la barra de progreso
	 * y construir la vista y sus adapters
	 * @author Carlos Granada | @cgranadax
	 * @since 27/04/2012
	 * @param latitude
	 * @param longitude
	 */
	  private void loadNearbyPlaces(final double latitude, final double longitude) {
		//@cgranadax: Se crea la barra de progreso
		 mProgress	=  new ProgressDialog(mapViewContext.getContext());
		 //@cgranadax: Se setea el mensaje de la caja de progreso para cargar sitios
		 mProgress.setMessage(AplicationContext.getValueStringResource(R.string.msg_consultando_fsq));
		 //@cgranadax: Se setea el icono y titulo de la barra de progreso
		 mProgress.setIcon(R.drawable.foursquare_logo);		 
		 mProgress.setTitle(AplicationContext.getValueStringResource(R.string.title_sitios_cercanos));
		//@cgranadax: Se muestra la barra de progreso
		 mProgress.show();	    	
	    	new Thread() {
	    		@Override
	    		public void run() {
	    			int what = 0;
	    			try {
	    				//@cgranadax: Se obtiene la lista de sitios cercanos de acuerdo a una latitud y longitud
	    				mNearbyList = estacionesCercanasBO.getNearby(latitude, longitude);
	    			} catch (Exception e) {
	    				what = 1;
	    				e.printStackTrace();
	    			}	    			
	    			mHandler.sendMessage(mHandler.obtainMessage(what));
	    		}
	    	}.start();
	    }
	    
	  /*
	   * @cgranadax Handler para hacer algo una vez se finaliza la tarea de consulta de los sitios cercanos o hay una excepcion
	   */
	    private Handler mHandler = new Handler() {
	    	@Override
	    	public void handleMessage(Message msg) {
	    		//@cgranadax: Se destruye la caja de progreso
	    		mProgress.dismiss();
	    		//@cgranadax: Si el mensaje es cero quiere decir que hay respuesta correcta
	    		if (msg.what == 0) {
	    			if (mNearbyList.size() == 0) {
	    				//@cgranadax: Se muestra un mensaje de no sitios cercanos en caso de que la lista retorne 0
	    				Toast.makeText(mapViewContext.getContext(), AplicationContext.getValueStringResource(R.string.no_hay_sitios_cercanos), Toast.LENGTH_SHORT).show();
	    				return;
	    			}
	    			//@cgranadax: En caso de que existan sitios cercanos a una estación se construye la caja de dialogo custom
	    			Dialog dlg = new Dialog(mapViewContext.getContext());
	    			//@cgranadax: Se indica que va a haber icono en la parte izquierda
	    			dlg.requestWindowFeature(Window.FEATURE_LEFT_ICON);
	    			//@cgranadax: Se setea el titulo del dialogo custom
	    			dlg.setTitle(AplicationContext.getValueStringResource(R.string.cerca_a)+" "+estacionMetroActualDTO.getNombre());
	    			//@cgranadax: Se setea el layout custom para la caja de dialogo custom
	    			LayoutInflater li = (LayoutInflater) mapViewContext.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    			View v = li.inflate(R.layout.lugares_cercanos_fsq, null, false);
	    			dlg.setContentView(v);
	    			//@cgranadax: Se crea el array adapter para poblar la caja de dialogo con lugares cercanos
	    			lugaresCercanosListAdapter = new LugaresCercanosListAdapter(dlg.getContext(), mNearbyList);
	    			l_v_lugares = (ListView) dlg.findViewById(R.id.listViewLugaresCercanos);
	    			l_v_lugares.setAdapter(lugaresCercanosListAdapter);
	    			//@cgranadax: Se muestra la caja de dialogo y se setea el icono
	    			dlg.show();
	    			dlg.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, getImageEstacion(estacionMetroActualDTO));
	    		}else {
	    			//@cgranadax: Se muestra un mensaje de error en caso de que la respuesta no sea cero
	    			Toast.makeText(mapViewContext.getContext(), AplicationContext.getValueStringResource(R.string.err_carga_sitios_cercanos), Toast.LENGTH_SHORT).show();
	    		}
	    	}
	    };
}