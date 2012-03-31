package co.droidforum.metromed.activities.adapters;

import java.util.List;

import co.droidforum.metromed.R;
import co.droidforum.metromed.dto.EstacionMetroDTO;
import android.content.Context;
import android.widget.ArrayAdapter;

public class AlimentadoresListAdapter extends ArrayAdapter<EstacionMetroDTO> {
	private Context context;
	private List<EstacionMetroDTO> datos;
	public AlimentadoresListAdapter(Context context, List<EstacionMetroDTO> objects){
		super(context, R.layout.list_view_alimentadores);
		datos = objects;
		this.context = context;
	}
}
