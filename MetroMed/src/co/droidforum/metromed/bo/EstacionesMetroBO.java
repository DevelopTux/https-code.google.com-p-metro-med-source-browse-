package co.droidforum.metromed.bo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.inject.Inject;

import android.content.ContentValues;
import android.content.res.Resources.NotFoundException;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import co.droidforum.metromed.R;
import co.droidforum.metromed.application.AplicationContext;
import co.droidforum.metromed.application.BusinessObject;
import co.droidforum.metromed.dao.EstacionesDAO;
import co.droidforum.metromed.dto.EstacionMetroDTO;

import com.google.android.maps.GeoPoint;


/**
 * 
 * Clase encargada de implementar toda la lógica de negocio asociada para mostrar los mapas
 * segun la ubicación de la persona
 * 
 * @author Carlos Daniel Muñoz Idarraga | @cdmunozi
 *
 */
@BusinessObject
public class EstacionesMetroBO {

	//hace el llamado directo al dao, requiere importar las libs de guice y javax.inject
	@Inject EstacionesDAO estacionesDAO;
	
	/**
	 * Obtiene todas las estaciones de metro configuradas en la tabla
	 * @return lista de estaciones de metro
	 */
	public List<EstacionMetroDTO> getAllEstacionesMetro() {
		return estacionesDAO.getAllEstacionesMetro();
	}
	
	/**
	 * Metodo que obtiene las estaciones de una linea en especifico
	 * @param linea
	 * @return
	 */
	public List<EstacionMetroDTO> getEstacionesXLinea(String linea){
		return estacionesDAO.getEstacionesXLinea(linea);
	}
	/**
	 * Metodo llamado al momento de cargar la app para que inserte todas las estaciones de metro definidas en el 
	 * archivo de propiedades
	 */
	public void insertRecordsEstacionesMetro(){
		
		String record = "";
		ContentValues cv = null;
		StringTokenizer stringTokenizer = null;
		int cantidadRecordsBD = 0;
		int cantidadRecordsFile = 0;
		List<EstacionMetroDTO> estacionesMetro = null;
			
		try {
			//obtiene la cantidad de registros actuales
			estacionesMetro = estacionesDAO.getAllEstacionesMetro();
			if(estacionesMetro != null && !estacionesMetro.isEmpty()){
				cantidadRecordsBD = estacionesMetro.size();
			}
			
			InputStream rawResource = AplicationContext.getRawResource(R.raw.estaciones_metro);
		    Properties properties = new Properties();
		    properties.load(rawResource);
		    cantidadRecordsFile = properties.size();
		    
		    if(cantidadRecordsBD != cantidadRecordsFile){
		    	estacionesDAO.deleteEstacionesMetro(AplicationContext.getValueStringResource(R.string.nombre_tabla_estaciones_metro));
		    	for(int i=1; i<=cantidadRecordsFile; i++){
			    	record = properties.getProperty(String.valueOf(i));
			    	stringTokenizer = new StringTokenizer(record,"|");
			    	while (stringTokenizer.hasMoreElements()){
			    		cv = new ContentValues();
				    	cv.put("nombre", (String)stringTokenizer.nextElement());
						cv.put("latitud", (String)stringTokenizer.nextElement());
						cv.put("longitud", (String)stringTokenizer.nextElement());
						cv.put("linea", (String)stringTokenizer.nextElement());
						cv.put("imagen", (String)stringTokenizer.nextElement());
						estacionesDAO.insertEstacionesMetro(cv);
			    	}
			    }
		    }
		    
		    
		    rawResource.close();
		} catch (NotFoundException e) {
		    System.err.println("Did not find raw resource: " + e);
		} catch (IOException e) {
		    System.err.println("Failed to open microlog property file");
		}
	}
	
	
	public GeoPoint obtenerLocalizacion(LocationManager locManager){
		
		GeoPoint geoPoint = null;
		String bestProvider = "";
		final LocationManager locManagerTmp = locManager;
		LocationListener locListener = null;
		
		List<String> providers = locManager.getAllProviders();
		for (String provider : providers) {
			Log.e("-------------- Providers: ", provider);
		}
		
		Criteria criteria = new Criteria();
		bestProvider = locManager.getBestProvider(criteria, false);
		final String bestProviderTmp = bestProvider;
		
		Location location = locManager.getLastKnownLocation(bestProvider);
		Log.e("-------------- location: ", location!=null?location.toString():"Sin locacion");
		
		if(location != null){
			geoPoint = getPosicionActual(location);
		}
		
		
    	//Se crea un listener que hace las llamadas necesarias en callback 
    	//Nos registramos para recibir actualizaciones de la posición
    	locListener = new LocationListener() {
	    	public void onLocationChanged(Location location) {
	    		locManagerTmp.requestLocationUpdates(bestProviderTmp, 20000, 1, this);
	    		getPosicionActual(location);
	    		locManagerTmp.removeUpdates(this);
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
    	locManager.requestLocationUpdates(bestProvider, 20000, 1, locListener);
    	locManager.removeUpdates(locListener);
    	
    	return geoPoint;
	}
	
	/*
	 * Este metodo lo que hace es setear una ubicacion basada en el objeto Location que puede tener informacion de alguna posicion anterior
	 * o la nueva posicion obtenida. El objeto Location tiene en sus atributos la longitud y la latitud, que sirven como parametros para
	 * la ubicacion que se hace con el objeto GeoPoint
	 */
	private GeoPoint getPosicionActual(Location location) {
    	GeoPoint geoPoint = null;
		if(location != null)
    	{
    		Toast.makeText(AplicationContext.getContextApp(), AplicationContext.getValueStringResource(R.string.msg_toast_carga_mapa), Toast.LENGTH_LONG).show();
    		geoPoint = new GeoPoint(new Double(location.getLatitude()*1E6).intValue(), new Double(location.getLongitude()*1E6).intValue());
    		
    	}
		return geoPoint;
    }
}
