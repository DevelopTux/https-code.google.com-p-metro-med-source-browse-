package co.droidforum.metromed.bo;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import co.droidforum.metromed.R;
import co.droidforum.metromed.application.AplicationContext;
import co.droidforum.metromed.application.BusinessObject;
import co.droidforum.metromed.dto.FoursquareVenueDTO;
import co.droidforum.metromed.utils.Utilidades;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

/**
 * Clase que consulta los lugares cercanos de foursquare y su web service
 * @author Carlos Granada | @cgranadax
 * @since27/04/2012
 */

@BusinessObject
public class FoursquareBO {
	//@cgranadax: URL del API de foursquare
	private static final String API_URL = AplicationContext.getValueStringResource(R.string.foursquare_api_url);
	
	/**
	 * Metodo para obtener los lugares cercanos con foursaquare: Ejemplo adaptado de : http://blog.doityourselfandroid.com/2011/09/05/integrate-foursquare-android-application/ 
	 * @param latitude
	 * @param longitude
	 * @return
	 * @throws Exception
	 */
	public ArrayList<FoursquareVenueDTO> getNearby(double latitude, double longitude) throws Exception {
		ArrayList<FoursquareVenueDTO> venueList = new ArrayList<FoursquareVenueDTO>();		
		try {
			//@cgranadax: Latitud y longitud para la consulta del API de foursquare
			String ll 	= String.valueOf(latitude) + "," + String.valueOf(longitude);			
			String urlStr = API_URL + ll + "&"+AplicationContext.getValueStringResource(R.string.xid) +"&" + AplicationContext.getValueStringResource(R.string.xsec) +"&v="+Utilidades.getCurrentTimeStampyyyyMMdd();
			Log.d("FECHA: ", Utilidades.getCurrentTimeStampyyyyMMdd());
			//@cgranadax: Se usa restTemplate por compatibilidad con Android ICS
			RestTemplate restTemplate = new RestTemplate();			
			ResponseEntity<String> entity = restTemplate.getForEntity(urlStr, String.class);
			String response		= entity.getBody();
			JSONObject jsonObj 	= (JSONObject) new JSONTokener(response).nextValue();
			JSONArray items	= (JSONArray) jsonObj.getJSONObject("response").getJSONArray("venues");
			//@cgranadax: Se recorren los lugares cercanos
			for (int i = 0; i < items.length(); i++) {
				JSONObject item = (JSONObject) items.get(i);					
				FoursquareVenueDTO venue 	= new FoursquareVenueDTO();					
				venue.id 		= item.getString("id");
				venue.name		= item.getString("name");
				JSONObject location = (JSONObject) item.getJSONObject("location");
				Location loc 	= new Location(LocationManager.GPS_PROVIDER);
				loc.setLatitude(Double.valueOf(location.getString("lat")));
				loc.setLongitude(Double.valueOf(location.getString("lng")));
				venue.location	= loc;
				if(!location.isNull("address"))
					venue.address	= location.getString("address");
				venue.distance	= location.getInt("distance");
				venue.herenow	= item.getJSONObject("hereNow").getInt("count");
				venue.type		= "TIPO";
				
				venueList.add(venue);
			}

		} catch (Exception ex) {
			throw ex;
		}
		
		return venueList;
	}
	
	
}
