package co.droidforum.metromed.dto;

/**
 * 
 * Clase que encapsula la informacion de una estacion de metro
 * 
 * @author Carlos Daniel Muñoz Idárraga | @cdmunozi
 * @since  30/03/2012
 *
 */
public class EstacionMetroDTO {

	private String id;
	private String nombre;
	private String latitud;
	private String longitud;
	private String linea;
	private String imagen;
	
	/**
	 * Constructor por defecto
	 */
	public EstacionMetroDTO() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getLinea() {
		return linea;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}
	
	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
}
