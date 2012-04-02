package co.droidforum.metromed.bo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.inject.Inject;

import android.content.ContentValues;
import android.content.res.Resources.NotFoundException;
import co.droidforum.metromed.R;
import co.droidforum.metromed.application.AplicationContext;
import co.droidforum.metromed.application.BusinessObject;
import co.droidforum.metromed.dao.EstacionesDAO;
import co.droidforum.metromed.dto.EstacionMetroDTO;


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
		    }
		    
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
		    rawResource.close();
		} catch (NotFoundException e) {
		    System.err.println("Did not find raw resource: " + e);
		} catch (IOException e) {
		    System.err.println("Failed to open microlog property file");
		}
	}
}
