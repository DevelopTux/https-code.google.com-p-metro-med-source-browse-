package co.droidforum.metromed.activities.alimentadores;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import co.droidforum.metromed.R;
import co.droidforum.metromed.activities.adapters.AlimentadoresListAdapter;
import co.droidforum.metromed.activities.commons.DashboardMainActivity;
import co.droidforum.metromed.application.AplicationContext;
import co.droidforum.metromed.application.BusinessContext;
import co.droidforum.metromed.application.GenericActivity;
import co.droidforum.metromed.bo.EstacionesMetroBO;
import co.droidforum.metromed.dto.EstacionMetroDTO;

public class AlimentadoresActivity extends GenericActivity {
	
	private ListView alimentadoresListView;
	private Button buttonLineaA;
	private Button buttonLineaB;
	private Button buttonLineaJ;
	private AlimentadoresListAdapter alimentadoresListAdapter;
	EstacionesMetroBO estacionMetroBO;
	private ImageView metroMedLogoImg;
	private ImageView galaxyLogoImg;
	
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
		
		//para hacer navegable al home
        metroMedLogoImg = (ImageView)findViewById(R.id.metromedlogoimg);
        //Logo Metro Med
		metroMedLogoImg.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View v){
		        Intent intent = new Intent(AplicationContext.getContextApp(), DashboardMainActivity.class);
		        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(intent);
		    }
		});
		
		//para cargar web de galaxy movil
		galaxyLogoImg = (ImageView)findViewById(R.id.galaxylogoimg);
		galaxyLogoImg.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View v){
		        Intent intent = new Intent();
		        intent.setAction(Intent.ACTION_VIEW);
		        intent.addCategory(Intent.CATEGORY_BROWSABLE);
		        intent.setData(Uri.parse(getResources().getString(R.string.url_galaxymovil)));
		        startActivity(intent);
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
