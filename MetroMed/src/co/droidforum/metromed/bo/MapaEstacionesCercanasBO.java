package co.droidforum.metromed.bo;

import java.util.List;

import javax.inject.Inject;

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
public class MapaEstacionesCercanasBO {

	//hace el llamado directo al dao, requiere importar las libs de guice y javax.inject
	@Inject EstacionesDAO estacionesDAO;
	
	/**
	 * Obtiene todas las estaciones de metro configuradas en la tabla
	 * @return lista de estaciones de metro
	 */
	public List<EstacionMetroDTO> getAllEstacionesMetro() {
		return estacionesDAO.getAllEstacionesMetro();
	}
}
