package co.droidforum.metromed.activities.adapters;

import java.util.List;

import co.droidforum.metromed.R;
import co.droidforum.metromed.activities.alimentadores.MapaAlimentadorActivity;
import co.droidforum.metromed.activities.mapametro.MapaMetroActivity;
import co.droidforum.metromed.application.AplicationContext;
import co.droidforum.metromed.application.GenericActivity;
import co.droidforum.metromed.dto.EstacionMetroDTO;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AlimentadoresListAdapter extends ArrayAdapter<EstacionMetroDTO> {
	
	private Context context;
	private List<EstacionMetroDTO> datos;
	
	public AlimentadoresListAdapter(Context context, List<EstacionMetroDTO> objects){
		super(context, R.layout.list_view_alimentadores, objects);
		datos = objects;
		this.context = context;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {		
	    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
	    View item = inflater.inflate(R.layout.list_view_alimentadores, null);
	    if(position<datos.size()){
	    	final int positionFinal = position;
	    	EstacionMetroDTO estacionMetroDTO = datos.get(position);
	    	TextView titulo = (TextView)item.findViewById(R.id.nombreEstacionList);
		    titulo.setText(estacionMetroDTO.getNombre());		    
		    TextView subtitulo = (TextView)item.findViewById(R.id.subTextoEstacion);
		    subtitulo.setText(AplicationContext.getValueStringResource(R.string.linea_metro)+": "+estacionMetroDTO.getLinea());
		    item.setOnClickListener(new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	if(datos.get(positionFinal).getImagen()!=null&&!datos.get(positionFinal).getImagen().trim().equals("-")){
		        		Intent intent = new Intent(GenericActivity.getActualActivity(), MapaAlimentadorActivity.class);
			        	intent.putExtra("imagen", datos.get(positionFinal).getImagen());
			        	GenericActivity.getActualActivity().startActivity(intent);
		        	}else{
		        		Toast.makeText(context, AplicationContext.getValueStringResource(R.string.no_existe_mapa_alimentador)+" "+datos.get(positionFinal).getNombre(), Toast.LENGTH_LONG).show();
		        	}
		        	
		        }
		    });
		    item.setClickable(true);
	    }	    
	    return (item);
   }
	
	
	
	public List<EstacionMetroDTO> getDatos() {
		return datos;
	}

    
	public void setDatos(List<EstacionMetroDTO> datos) {
		this.datos = datos;
	}	
}
