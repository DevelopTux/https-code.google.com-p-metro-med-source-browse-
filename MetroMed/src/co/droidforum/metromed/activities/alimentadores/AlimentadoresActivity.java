package co.droidforum.metromed.activities.alimentadores;

import java.util.List;

import co.droidforum.metromed.R;
import co.droidforum.metromed.activities.adapters.AlimentadoresListAdapter;
import co.droidforum.metromed.activities.commons.DashboardMainActivity;
import co.droidforum.metromed.activities.mapametro.MapaMetroActivity;
import co.droidforum.metromed.application.AplicationContext;
import co.droidforum.metromed.application.BusinessContext;
import co.droidforum.metromed.application.GenericActivity;
import co.droidforum.metromed.bo.EstacionesMetroBO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import co.droidforum.metromed.dto.*;

public class AlimentadoresActivity extends GenericActivity {
	
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
		/*
		 * por defecto se carga la linea A 
		 */
		setEstacionesListAdapter(getResources().getString(R.string.linea_A_validacion));
		
		buttonLineaA.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {			
				setEstacionesListAdapter(getResources().getString(R.string.linea_A_validacion));
			}
		});
		
		buttonLineaB.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {			
				setEstacionesListAdapter(getResources().getString(R.string.linea_B_validacion));
			}
		});
		buttonLineaJ.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {			
				setEstacionesListAdapter(getResources().getString(R.string.linea_J_validacion));
			}
		});
		
	}
	
	public void setEstacionesListAdapter(String linea){
		alimentadoresListAdapter = new AlimentadoresListAdapter(this, getListaEstacionesMetro(linea));
		alimentadoresListView.setAdapter(alimentadoresListAdapter);
		alimentadoresListAdapter.notifyDataSetChanged();
		
	}
	
	private List<EstacionMetroDTO> getListaEstacionesMetro(String linea){
		return estacionMetroBO.getEstacionesXLinea(linea);
	}	
	
}
