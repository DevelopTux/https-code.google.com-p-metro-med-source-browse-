package co.droidforum.metromed.activities.estacionesmapa;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import co.droidforum.metromed.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

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
			mapController.setZoom(18);
		}
	}
	
	/*
	 * Metodo que obtiene la ubicacion actual basado en los sistemas de REDES de celular y WiFi
	 */
	private void comenzarLocalizacion(){
		
    	//Obtenemos una referencia al LocationManager
    	locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	
    	//Obtenemos la última posición conocida
    	Location location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    	//se muestra esa posicion
    	getPosicionActual(location);
    	
    	//Se crea un listener que hace las llamadas necesarias en callback 
    	//Nos registramos para recibir actualizaciones de la posición
    	locListener = new LocationListener() {
	    	public void onLocationChanged(Location location) {
	    		getPosicionActual(location);
	    	}
	    	public void onProviderDisabled(String provider){}
	    	public void onProviderEnabled(String provider){}
	    	public void onStatusChanged(String provider, int statusProvider, Bundle extras){}
    	};

    	/*
    	 * Registra el listener en el Location Manager para recibir actualizaciones de locacion de las redes
    	 * Los parametros 2 y 3 de requestLocationUpdates estan en 0 para indicar que nos de la posicion en vivo
    	 * con el minimo intervalo de tiempo entre notificaciones.
    	 * Al usar localizacion por Redes, debe agregarse el permiso android.permission.ACCESS_COARSE_LOCATION
    	 * en el AndroidManifest.
    	 */
    	locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, locListener);
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
    		Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_toast_carga_mapa), Toast.LENGTH_LONG).show();
    		geoPoint = new GeoPoint(new Double(location.getLatitude()*1E6).intValue(), new Double(location.getLongitude()*1E6).intValue());
    		
    	}
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
}
