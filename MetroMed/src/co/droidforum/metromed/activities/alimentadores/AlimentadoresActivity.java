package co.droidforum.metromed.activities.alimentadores;

import java.util.List;

import co.droidforum.metromed.R;
import co.droidforum.metromed.activities.adapters.AlimentadoresListAdapter;
import co.droidforum.metromed.application.AplicationContext;
import co.droidforum.metromed.application.BusinessContext;
import co.droidforum.metromed.bo.EstacionesMetroBO;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import co.droidforum.metromed.dto.*;

public class AlimentadoresActivity extends Activity {
	
	private ListView alimentadoresListView;
	private AlimentadoresListAdapter alimentadoresListAdapter;
	EstacionesMetroBO estacionMetroBO;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		estacionMetroBO = BusinessContext.getBean(EstacionesMetroBO.class);
		setContentView(R.layout.alimentadores_metro);		
		/*
		 * Se inicializan los componentes 
		 */
		
		alimentadoresListView = (ListView)findViewById(R.id.listViewAlimentadores);
		alimentadoresListAdapter = new AlimentadoresListAdapter(this, getListaEstacionesMetro());
		alimentadoresListView.setAdapter(alimentadoresListAdapter);
		//registerForContextMenu(alimentadoresListView);
		
	}
	
	private List<EstacionMetroDTO> getListaEstacionesMetro(){
		return estacionMetroBO.getEstacionesXLinea("A");
	}
}
