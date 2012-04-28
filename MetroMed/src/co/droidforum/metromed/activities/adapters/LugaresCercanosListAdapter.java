package co.droidforum.metromed.activities.adapters;

import java.util.List;

import co.droidforum.metromed.R;
import co.droidforum.metromed.application.AplicationContext;
import co.droidforum.metromed.dto.FoursquareVenueDTO;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Clase encargada de construir el arrayadapter de los lugares cercanos
 * @author Carlos Granada | @cgranadax
 * @since 26/04/2012
 */
public class LugaresCercanosListAdapter extends ArrayAdapter<FoursquareVenueDTO> {
	private List<FoursquareVenueDTO> datos;
	
	public LugaresCercanosListAdapter(Context context, List<FoursquareVenueDTO> objects){
		super(context, R.layout.list_view_alimentadores, objects);
		datos = objects;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {		
		LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View item = inflater.inflate(R.layout.list_view_lugares_cercanos_fsq, null);
    	FoursquareVenueDTO foursquareVenueDTO = datos.get(position);
    	TextView distancia = (TextView)item.findViewById(R.id.tv_distance);
	    distancia.setText(Integer.toString(foursquareVenueDTO.getDistance())+AplicationContext.getValueStringResource(R.string.unidad_medida_distance));	
	    TextView nombre = (TextView)item.findViewById(R.id.tv_name);
	    nombre.setText(foursquareVenueDTO.getName());		    
	    TextView direccion = (TextView)item.findViewById(R.id.tv_address);
	    direccion.setText(foursquareVenueDTO.getAddress());		    
	    TextView personasaqui = (TextView)item.findViewById(R.id.tv_here_now);
	    personasaqui.setText(AplicationContext.getValueStringResource(R.string.pesronas_aqui)+" "+Integer.toString(foursquareVenueDTO.getHerenow()));		    
	    
	    item.setClickable(true);
        return (item);
   }
	
	public List<FoursquareVenueDTO> getDatos() {
		return datos;
	}

    
	public void setDatos(List<FoursquareVenueDTO> datos) {
		this.datos = datos;
	}	
}
