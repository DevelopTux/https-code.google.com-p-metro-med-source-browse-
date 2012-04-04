package co.droidforum.metromed.activities.estacionesmapa;

import java.util.List;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import co.droidforum.metromed.R;
import co.droidforum.metromed.bo.EstacionesMetroBO;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.estaciones_cercanas);
		
		comenzarLocalizacion();
		
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
			mapController.setZoom(14);
			
			//Dibuja el punto en el cual estoy ubicado
			setMyPoint(mapView,geoPoint);
		}
	}
	
	/*
	 * Metodo que obtiene la ubicacion actual basado en los sistemas de REDES de celular y WiFi
	 */
	private void comenzarLocalizacion(){
		
    	//Obtenemos una referencia al LocationManager
    	locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	geoPoint = new EstacionesMetroBO().obtenerLocalizacion(locManager);

    }
	
	/*
	 * Basado en el mapa actual y la posicion actual marcada como centro del mapa, en ese punto ubico un icono de posicion
	 */
	private void setMyPoint(MapView mapView, GeoPoint geoPoint){
		
		Projection projection = mapView.getProjection();
		//Añadimos la capa de marcas
		List<Overlay> capas = mapView.getOverlays();
		OverlayMapa overlayMapa = new OverlayMapa(geoPoint,projection);
		capas.add(overlayMapa);
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
