package co.droidforum.metromed.bo;

import java.util.List;

import javax.inject.Inject;

import android.content.ContentValues;

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
	
	public void insertOneRecord(){
		ContentValues cv = new ContentValues();
		cv.put("nombre", "Niquia");
		cv.put("latitud", "6");
		cv.put("longitud", "-75");
		cv.put("linea", "A");
		
		estacionesDAO.insertEstacionesMetro(cv);
	}
}
