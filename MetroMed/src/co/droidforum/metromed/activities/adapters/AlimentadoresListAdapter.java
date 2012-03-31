package co.droidforum.metromed.activities.adapters;

import java.util.List;

import co.droidforum.metromed.R;
import co.droidforum.metromed.dto.EstacionMetroDTO;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AlimentadoresListAdapter extends ArrayAdapter<EstacionMetroDTO> {
	private Context context;
	private List<EstacionMetroDTO> datos;
	public AlimentadoresListAdapter(Context context, List<EstacionMetroDTO> objects){
		super(context, R.layout.list_view_alimentadores);
		datos = objects;
		this.context = context;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
	    View item = inflater.inflate(R.layout.list_view_alimentadores, null);
	    EstacionMetroDTO estacionMetroDTO = datos.get(position);
	    TextView titulo = (TextView)item.findViewById(R.id.nombreEstacionList);
	    titulo.setText(estacionMetroDTO.getNombre());
	    
	    TextView subtitulo = (TextView)item.findViewById(R.id.subTextoEstacion);
	    subtitulo.setText("Linea: "+estacionMetroDTO.getLinea());
	    return item;
   }
}
