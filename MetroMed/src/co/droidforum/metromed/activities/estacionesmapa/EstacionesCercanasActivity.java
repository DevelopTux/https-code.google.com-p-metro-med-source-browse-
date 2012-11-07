package co.droidforum.metromed.activities.estacionesmapa;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import co.droidforum.metromed.R;
import co.droidforum.metromed.activities.commons.DashboardMainActivity;
import co.droidforum.metromed.application.AplicationContext;
import co.droidforum.metromed.utils.mapa.OverlayMapa;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

/**
 * Actividad encargada de cargar un mapa de Google y mostrar en éste la ubicación
 * actual de la persona basado en primera instancia en el GPS si esta activo, caso contrario
 * en las redes de celular.
 * Para cargar un mapa de Google la clase debe extender de la clase MapActivity y configurar
 * que se utilizaran las librerias de Google en el AndroidManifest
 * 
 * @author Carlos Daniel Muñoz Idarraga | @cdmunozi
 * @since 29/03/2012
 */
public class EstacionesCercanasActivity extends MapActivity {

	/*
	 * Objeto que contiene los metodos para cargar y mostrar el mapa
	 */
	private MapView mapView = null;
	private MapController mapController = null;
	private GeoPoint geoPoint = null;
	private LocationManager locManager = null;
	private LocationListener locListener = null;
	String bestProvider = "";
	private ImageView metroMedLogoImg;
	private ImageView droidforumLogoImg;
	private ImageButton imgBtn_center;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.estaciones_cercanas);
		
		//para hacer navegable al home
        metroMedLogoImg = (ImageView)findViewById(R.id.metromedlogoimg);
        //Logo Metro Med
		metroMedLogoImg.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View v){
		        Intent intent = new Intent(AplicationContext.getContextApp(), DashboardMainActivity.class);
		        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(intent);
		    }
		});
		
		//para cargar web de droidforum
		droidforumLogoImg = (ImageView)findViewById(R.id.droidforumlogoimg);
		droidforumLogoImg.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View v){
		        Intent intent = new Intent();
		        intent.setAction(Intent.ACTION_VIEW);
		        intent.addCategory(Intent.CATEGORY_BROWSABLE);
		        intent.setData(Uri.parse(getResources().getString(R.string.url_droidforum)));
		        startActivity(intent);
		    }
		});
		//obtiene la localizacion actual
		comenzarLocalizacion();
		
		//actualizar el mapa
		actualizarLocalizacionMapa();
		
		Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_toast_tocar_estacion), Toast.LENGTH_LONG).show();
		
		//para localizar el mapa en el punto donde estoy
		imgBtn_center = (ImageButton)findViewById(R.id.imgBtn_Center);
		imgBtn_center.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//obtiene la ubicación actual de nuevo para garantizar que si se movio, actualice la info
				comenzarLocalizacion();
				//actualiza la localizacion en el mapa
				actualizarLocalizacionMapa();
			}
		});
		
	}
	
	/*
	 * Metodo que obtiene la ubicacion actual basado en los sistemas de REDES de celular y WiFi
	 */
	private void comenzarLocalizacion(){
		
    	//Obtenemos una referencia al LocationManager
    	locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

    	List<String> providers = locManager.getAllProviders();
		for (String provider : providers) {
			Log.e("-------------- Providers: ", provider);
		}
		
		Criteria criteria = new Criteria();
		bestProvider = locManager.getBestProvider(criteria, false);
		
		Location location = locManager.getLastKnownLocation(bestProvider);
		Log.e("-------------- location: ", location!=null?location.toString():"Sin locacion");
		
		if(location != null){
			getPosicionActual(location);
		}
		
		/*
    	 * Registra el listener en el Location Manager para recibir actualizaciones de locacion de las redes
    	 * Los parametros 2 y 3 de requestLocationUpdates estan en 0 para indicar que nos de la posicion en vivo
    	 * con el minimo intervalo de tiempo entre notificaciones.
    	 * Al usar localizacion por Redes, debe agregarse el permiso android.permission.ACCESS_COARSE_LOCATION
    	 * en el AndroidManifest.
    	 */
//    	locManager.requestLocationUpdates(bestProvider, 20000, 1, locListener);
    	
    	//Se crea un listener que hace las llamadas necesarias en callback 
    	//Nos registramos para recibir actualizaciones de la posición
    	locListener = new LocationListener() {
	    	public void onLocationChanged(Location location) {
	    		locManager.requestLocationUpdates(bestProvider, 20000, 1, this);
	    		getPosicionActual(location);
	    		locManager.removeUpdates(this);
	    	}
	    	public void onProviderDisabled(String provider){}
	    	public void onProviderEnabled(String provider){}
	    	public void onStatusChanged(String provider, int statusProvider, Bundle extras){}
    	};
    	
    	locManager.requestLocationUpdates(bestProvider, 20000, 1, locListener);
    	locManager.removeUpdates(locListener);
    }
	
	/*
	 * Este metodo lo que hace es setear una ubicacion basada en el objeto Location que puede tener informacion de alguna posicion anterior
	 * o la nueva posicion obtenida. El objeto Location tiene en sus atributos la longitud y la latitud, que sirven como parametros para
	 * la ubicacion que se hace con el objeto GeoPoint
	 */
	private void getPosicionActual(Location location) {
    	if(location != null)
    	{
    		Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_toast_carga_mapa), Toast.LENGTH_SHORT).show();
    		geoPoint = new GeoPoint(new Double(location.getLatitude()*1E6).intValue(), new Double(location.getLongitude()*1E6).intValue());
    		
    	}
    }
	
	/*
	 * Despues de estar localizados, actualiza la info en el mapa
	 */
	private void actualizarLocalizacionMapa(){
		//Obtenemos una referencia al control MapView
		mapView = (MapView)findViewById(R.id.mapaEstacionesCercanas);
		
		//Mostramos los controles de zoom sobre el mapa
		mapView.setBuiltInZoomControls(true);
		
		//Seteo la vista del mapa como de trafico
		mapView.setTraffic(true);
		
		/*
		 * como la ubicacion por defecto en el mapa no se puede modificar, con un
		 * MapController podemos setear cualquier posicion en longitud y latitud 
		 */
		mapController = mapView.getController();
		
		//Si hay datos de longitud y latitud ubica en la posicion actual
		if(geoPoint != null){
			mapController.setCenter(geoPoint);
			//el minimo valor del zoom es 1 (vista menor detalle) y el maximo es 21 (vista mayor detalle)
			mapController.setZoom(15);
			
			//Dibuja el punto en el cual estoy ubicado
			setMyPoint(mapView,geoPoint);
		}
	}
	
	/*
	 * Basado en el mapa actual y la posicion actual marcada como centro del mapa, en ese punto ubico un icono de posicion
	 */
	private void setMyPoint(MapView mapView, GeoPoint geoPoint){
		
		Projection projection = mapView.getProjection();
		//Añadimos la capa de marcas
		List<Overlay> capas = mapView.getOverlays();
		Log.e("---------------", ""+capas.size());
		if(!capas.isEmpty()){
			capas.remove(0);
		}
		OverlayMapa overlayMapa = new OverlayMapa(geoPoint,projection);
		capas.add(overlayMapa);
		Log.e("---------------", ""+capas.size());
		//para redibujar el mapa y todas sus capas
		mapView.postInvalidate();
	}

	/*
     * El valor de retorno debe ser true sólo en caso de que vayamos a 
     * representar algún tipo de información de ruta sobre el mapa 
     * (esto no se trata de ningún tema técnico, sino tan sólo legal, para 
     * cumplir los términos de uso de la API de Google Maps).
     * Se pone false sólo para mostrar el mapa
     */
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	@Override
	protected void onPause() {
		locManager.removeUpdates(locListener);
		super.onPause();
	}
}
