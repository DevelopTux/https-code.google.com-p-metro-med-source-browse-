package co.droidforum.metromed.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Clase con utilidades varias apra aplicacion
 * 
 * @author Carlos Daniel Muñoz Idarraga | @cdmunozi
 * @since  30/03/2012
 *
 */
public class Utilidades {

	/**
	 * Metodo que retorna la fecha en formato yyyymmdd 
	 * @return
	 */
	public static String getCurrentTimeStampyyyyMMdd() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
	
}
