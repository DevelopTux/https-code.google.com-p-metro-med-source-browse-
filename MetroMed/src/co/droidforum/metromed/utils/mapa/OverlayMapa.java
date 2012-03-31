package co.droidforum.metromed.utils.mapa;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.widget.Toast;
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
			Bitmap bm = BitmapFactory.decodeResource(mapView.getResources(),R.drawable.marcador_google_maps);
			
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
			
			//Marca con una imagen apuntador como Google Maps
			Bitmap bm = BitmapFactory.decodeResource(mapView.getResources(),R.drawable.marcador_google_maps);
			
			canvas.drawBitmap(bm, centro.x - bm.getWidth(), 
					              centro.y - bm.getHeight(), p);
			
		}
	}
	
	@Override
	public boolean onTap(GeoPoint point, MapView mapView) 
	{
		
		String msg = "Lat: " + point.getLatitudeE6()/1E6 + " - " + 
		             "Lon: " + point.getLongitudeE6()/1E6;
		
		Toast toast = Toast.makeText(AplicationContext.getContextApp(), msg, Toast.LENGTH_SHORT);
		toast.show();
		
		return true;
	}
	
}
