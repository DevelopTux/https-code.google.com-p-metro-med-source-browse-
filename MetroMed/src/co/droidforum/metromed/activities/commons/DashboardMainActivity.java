package co.droidforum.metromed.activities.commons;

/**
 * Clase encargada de construir el menu principal y de determinar el comportamiento de
 * los botones que la contienen
 * @author DroidForum.co / GalaxyMovil.com / @cgranadax
 */

import co.droidforum.metromed.R;
import co.droidforum.metromed.activities.mapametro.MapaMetroActivity;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import co.droidforum.metromed.activities.alimentadores.AlimentadoresAActivity;
import co.droidforum.metromed.activities.estacionesmapa.EstacionesCercanasActivity;

public class DashboardMainActivity extends Activity {
	/*
	 * Elementos de la pantalla
	 */
	private ImageView galaxyLogoImg;
	private Button buttonEstacionesCercanas;
	private Button buttonMapaMetro;
	private Button buttonAlimentadores;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_main);
		
		/*
		 * Programando los eventos de cada elemento de la pantalla 
		 */
		galaxyLogoImg = (ImageView)findViewById(R.id.galaxylogoimg);
		buttonMapaMetro = (Button)findViewById(R.id.buttonMapaMetro);
		buttonAlimentadores = (Button)findViewById(R.id.buttonAlimentadores);
		galaxyLogoImg.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View v){
		        Intent intent = new Intent();
		        intent.setAction(Intent.ACTION_VIEW);
		        intent.addCategory(Intent.CATEGORY_BROWSABLE);
		        intent.setData(Uri.parse(getResources().getString(R.string.url_galaxymovil)));
		        startActivity(intent);
		    }
		});
		
		/*
		 * Evento para cargar actividad para ver las estaciones cercanas a mi
		 * Allí se carga un mapa de google en el cual se mostará la ubicación actual
		 * y las de las estaciones de Metro
		 */
		buttonEstacionesCercanas = (Button)findViewById(R.id.buttonEstacionesCercanas);
		buttonEstacionesCercanas.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(DashboardMainActivity.this, EstacionesCercanasActivity.class);
				startActivity(intent);				
			}
		});
		
		buttonMapaMetro.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {			
				Intent intent = new Intent(DashboardMainActivity.this, MapaMetroActivity.class);
				startActivity(intent);
			}
		});
		
		buttonAlimentadores.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {			
				Intent intent = new Intent(DashboardMainActivity.this, AlimentadoresAActivity.class);
				startActivity(intent);
			}
		});
	}
}
