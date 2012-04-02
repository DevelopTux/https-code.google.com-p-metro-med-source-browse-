package co.droidforum.metromed.activities.alimentadores;

import java.util.List;

import co.droidforum.metromed.R;
import co.droidforum.metromed.activities.adapters.AlimentadoresListAdapter;
import co.droidforum.metromed.activities.commons.DashboardMainActivity;
import co.droidforum.metromed.activities.mapametro.MapaMetroActivity;
import co.droidforum.metromed.application.AplicationContext;
import co.droidforum.metromed.application.BusinessContext;
import co.droidforum.metromed.bo.EstacionesMetroBO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import co.droidforum.metromed.dto.*;

public class AlimentadoresActivity extends Activity {
	
	private ListView alimentadoresListView;
	private Button buttonLineaA;
	private Button buttonLineaB;
	private Button buttonLineaJ;
	private AlimentadoresListAdapter alimentadoresListAdapter;
	EstacionesMetroBO estacionMetroBO;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		estacionMetroBO = BusinessContext.getBean(EstacionesMetroBO.class);
		setContentView(R.layout.alimentadores_metro);	
		buttonLineaA = (Button)findViewById(R.id.buttonLineaA);
		buttonLineaB = (Button)findViewById(R.id.buttonLineaB);
		buttonLineaJ = (Button)findViewById(R.id.buttonLineaJ);
		/*
		 * Se inicializan los componentes 
		 */
		alimentadoresListView = (ListView)findViewById(R.id.listViewAlimentadores);
		String linea=null;
		if(linea==null){
			linea = getResources().getString(R.string.linea_A_validacion);
		}
		alimentadoresListAdapter = new AlimentadoresListAdapter(this, getListaEstacionesMetro(linea));
		alimentadoresListView.setAdapter(alimentadoresListAdapter);
		
		buttonLineaB.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {			
				setEstacionesListAdapter(getResources().getString(R.string.linea_B_validacion));
			}
		});
		
	}
	
	public void setEstacionesListAdapter(String linea){
		//alimentadoresListAdapter.clear();
		alimentadoresListAdapter.setDatos(getListaEstacionesMetro(linea));
		alimentadoresListAdapter.notifyDataSetChanged();
	}
	
	private List<EstacionMetroDTO> getListaEstacionesMetro(String linea){
		return estacionMetroBO.getEstacionesXLinea(linea);
	}
}
