package co.droidforum.metromed.activities.alimentadores;

import java.util.List;

import co.droidforum.metromed.R;
import co.droidforum.metromed.activities.adapters.AlimentadoresListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import co.droidforum.metromed.dto.*;

public class AlimentadoresAActivity extends Activity {
	
	private ListView alimentadoresListView;
	private AlimentadoresListAdapter alimentadoresListAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alimentadores_metro);		
		/*
		 * Se inicializan los componentes 
		 */
		
		alimentadoresListView = (ListView)findViewById(R.id.listViewAlimentadores);
		alimentadoresListAdapter = new AlimentadoresListAdapter(this, getListaEstacionesMetro());
		alimentadoresListView.setAdapter(alimentadoresListAdapter);
	}
	
	private List<EstacionMetroDTO> getListaEstacionesMetro(){
		return null;
	}
}
