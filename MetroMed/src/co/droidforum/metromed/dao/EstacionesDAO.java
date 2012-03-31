package co.droidforum.metromed.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import co.droidforum.metromed.application.db.Binder;
import co.droidforum.metromed.application.db.GenericDAO;
import co.droidforum.metromed.dto.EstacionMetroDTO;

/**
 * 
 * Clase que realiza el acceso a datos con todo lo asociado a las estaciones
 * 
 * @author Carlos Daniel Muñoz Idárraga | @cdmunozi
 * @since  30/03/2012
 *
 */
public class EstacionesDAO extends GenericDAO {
	
	/**
	 * Obtiene todas las estaciones de metro configuradas en la tabla
	 * @return lista de estaciones de metro
	 */
	public List<EstacionMetroDTO> getAllEstacionesMetro() {
		String query = "select _id, nombre, latitud, longitud, linea from estaciones_metro";
		return getResultsByQuery(query, new EstacionMetroTodasBinder());
	}
	
	public List<EstacionMetroDTO> getEstacionesXLinea(String linea){
		/*
		 * Datos de prueba
		 */
		List<EstacionMetroDTO> listaDemo = new ArrayList<EstacionMetroDTO>();
		EstacionMetroDTO e=new EstacionMetroDTO();
		e.setNombre("Aguacatala");
		e.setLinea("A");
		listaDemo.add(e);
		e=new EstacionMetroDTO();
		e.setNombre("Aguacatalax");
		e.setLinea("A");
		listaDemo.add(e);
		e=new EstacionMetroDTO();
		e.setNombre("Aguacatalaxi");
		e.setLinea("A");
		listaDemo.add(e);
		return listaDemo;
	}
	
	private class EstacionMetroTodasBinder implements Binder<EstacionMetroDTO> {
		public EstacionMetroDTO bind(Cursor cursor){
			return getEstacionMetroDTO(cursor);
		}
	}
	
	/**
	 * Retorna un dto de estación del metro
	 * @return
	 */
	private EstacionMetroDTO getEstacionMetroDTO(Cursor cursor){
		EstacionMetroDTO estacionMetroDTO = new EstacionMetroDTO();
		estacionMetroDTO.setId(cursor.getString(cursor.getColumnIndex("_id")));
		estacionMetroDTO.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
		estacionMetroDTO.setLatitud(cursor.getString(cursor.getColumnIndex("latitud")));
		estacionMetroDTO.setLongitud(cursor.getString(cursor.getColumnIndex("longitud")));
		estacionMetroDTO.setLinea(cursor.getString(cursor.getColumnIndex("linea")));
		return estacionMetroDTO;
	}
	
	/**
	 * 
	 * Inserta en la tabla de estaciones
	 * 
	 * @param values Objeto ContentValues con la data a insertar
	 */
	public void insertEstacionesMetro(ContentValues values){		
		insert(values, "estaciones_metro");
	}

}
