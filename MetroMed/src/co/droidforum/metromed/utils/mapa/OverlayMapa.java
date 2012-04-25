package co.droidforum.metromed.utils.mapa;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import co.droidforum.metromed.R;
import co.droidforum.metromed.application.AplicationContext;
import co.droidforum.metromed.application.BusinessContext;
import co.droidforum.metromed.bo.EstacionesMetroBO;
import co.droidforum.metromed.dto.EstacionMetroDTO;

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
public class OverlayMapa extends Overlay {

	/*
	 * Tendrá en cuenta la posición sobre la que está centrada el mapa en este momento y el nivel de zoom aplicado para convertir 
	 * convenientemente entre latitud-longitud en grados y coordenadas x-y en pixeles
	 */
	private GeoPoint geoPoint;
	private Projection projection;
	private Bitmap iconoGral;
	
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
			Bitmap bm = null;
			if(estacionMetroDTO.getLinea().equalsIgnoreCase(AplicationContext.getValueStringResource(R.string.linea_A_validacion))){
				bm = BitmapFactory.decodeResource(mapView.getResources(),R.drawable.icono_a40);
			}else if(estacionMetroDTO.getLinea().equalsIgnoreCase(AplicationContext.getValueStringResource(R.string.linea_B_validacion))){
				bm = BitmapFactory.decodeResource(mapView.getResources(),R.drawable.icono_b40);
			}else if(estacionMetroDTO.getLinea().equalsIgnoreCase(AplicationContext.getValueStringResource(R.string.linea_J_validacion))){
				bm = BitmapFactory.decodeResource(mapView.getResources(),R.drawable.icono_j40);
			}else if(estacionMetroDTO.getLinea().equalsIgnoreCase(AplicationContext.getValueStringResource(R.string.linea_K_validacion))){
				bm = BitmapFactory.decodeResource(mapView.getResources(),R.drawable.icono_k40);
			}else if(estacionMetroDTO.getLinea().equalsIgnoreCase(AplicationContext.getValueStringResource(R.string.linea_L_validacion))){
				bm = BitmapFactory.decodeResource(mapView.getResources(),R.drawable.icono_l40);
			}else if(estacionMetroDTO.getLinea().equalsIgnoreCase(AplicationContext.getValueStringResource(R.string.linea_1_validacion))){
				bm = BitmapFactory.decodeResource(mapView.getResources(),R.drawable.icono_140);
			}else if(estacionMetroDTO.getLinea().equalsIgnoreCase(AplicationContext.getValueStringResource(R.string.linea_B_validacion))){
				bm = BitmapFactory.decodeResource(mapView.getResources(),R.drawable.icono_b50);
			}
			
			canvas.drawBitmap(bm, centro.x - bm.getWidth(), 
					              centro.y - bm.getHeight(), p);
			
		}
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
		EstacionesMetroBO estacionesCercanasBO = BusinessContext.getBean(EstacionesMetroBO.class);
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
    			AlertDialog.Builder builder = new AlertDialog.Builder(mapView.getContext());
    	 		builder.setCancelable(false)
    	 		       .setPositiveButton(R.string.alertDialogHorariosAceptar, new DialogInterface.OnClickListener() {
    	 		           public void onClick(DialogInterface dialog, int id) {
    	 		                //no hace nada sólo volver a la actividad
    	 		           }
    	 		       });
    	 		AlertDialog alert = builder.create();
    	 		alert.setTitle(estacionMetroDTO.getNombre());
    	 		alert.setIcon(R.drawable.icono_aqui);
    	 		alert.setMessage("Listado de sitios cercanos");
    	 		alert.show();
    			
    			break;
    		}
		}
		
		//limpia la seleccion
		point = null;
		
		return true;
	}	
}