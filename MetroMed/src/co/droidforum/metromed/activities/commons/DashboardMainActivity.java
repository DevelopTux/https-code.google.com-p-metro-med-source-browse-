package co.droidforum.metromed.activities.commons;

/**
 * Clase encargada de construir el menu principal y de determinar el comportamiento de
 * los botones que la contienen
 * @author DroidForum.co / GalaxyMovil.com / @cgranadax
 */

import co.droidforum.metromed.R;
import co.droidforum.metromed.activities.mapametro.MapaMetroActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.view.ViewGroup;
import co.droidforum.metromed.activities.alimentadores.AlimentadoresActivity;
import co.droidforum.metromed.activities.estacionesmapa.EstacionesCercanasActivity;
import co.droidforum.metromed.application.AplicationContext;

public class DashboardMainActivity extends Activity {
	/*
	 * Elementos de la pantalla
	 */
	private ImageView galaxyLogoImg;
	private Button buttonEstacionesCercanas;
	private Button buttonMapaMetro;
	private Button buttonAlimentadores;
	private Button buttonDashBoardInfoBottomBar;
	private Button buttonDashBoardTwitterBottomBar;
	
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
		buttonDashBoardInfoBottomBar = (Button)findViewById(R.id.buttonDashBoardInfoBottomBar);
		buttonDashBoardTwitterBottomBar = (Button)findViewById(R.id.buttonDashBoardTwitterBottomBar);
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
				Intent intent = new Intent(DashboardMainActivity.this, AlimentadoresActivity.class);
				startActivity(intent);
			}
		});
		
		buttonDashBoardInfoBottomBar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {			
				dialogButtonInfo();
			}
		});
		
		buttonDashBoardTwitterBottomBar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {			
				dialogSiguenosTwitter();
			}
		});
	}
	

 	/**
 	 * Metodo que genera un cuadro de dialogo de alerta con la información de la aplicación, como versión 
 	 * desarrolladores, licencia y etc
 	 */
 	private void dialogButtonInfo() {
 		AlertDialog.Builder builder = new AlertDialog.Builder(this);
 		builder.setCancelable(false)
 		       .setPositiveButton(R.string.alertDialogTarifaAceptar, new DialogInterface.OnClickListener() {
 		           public void onClick(DialogInterface dialog, int id) {
 		                //no hace nada sólo volver a la actividad
 		           }
 		       });
 		AlertDialog alert = builder.create();
 		alert.setTitle(R.string.alertDialogInfoMetroMedTitle);
 		alert.setMessage(AplicationContext.getValueStringResource(R.string.alertDialogInfoMetroMedText));
 		alert.show();
 		
 	}
 	
 	/**
 	 * Metodo que construye la caja de dialogo para el siguenos en twitter
 	 * Fuente: http://stackoverflow.com/questions/3920640/how-to-add-icon-in-alert-dialog-before-each-item
 	 * @author @cgranadax
 	 */
 	private void dialogSiguenosTwitter(){
 		final SiguenosItem[] items = {
 				new SiguenosItem(getString(R.string.tdroidforumco), R.drawable.droidforumco60),
 			    new SiguenosItem(getString(R.string.tandresarango), R.drawable.andy60),
 			    new SiguenosItem(getString(R.string.tcarlosdaniel), R.drawable.cdmunoz60),
 			    new SiguenosItem(getString(R.string.tcarlosgranada), R.drawable.cgranadax60),
 			};

		ListAdapter adapter = new ArrayAdapter<SiguenosItem>(
		    this,
		    android.R.layout.select_dialog_item,
		    android.R.id.text1,
		    items){
		        public View getView(int position, View convertView, ViewGroup parent) {
		            //User super class to create the View
		            View v = super.getView(position, convertView, parent);
		            TextView tv = (TextView)v.findViewById(android.R.id.text1);

		            //Put the image on the TextView
		            tv.setCompoundDrawablesWithIntrinsicBounds(items[position].icon, 0, 0, 0);

		            //Add margin between image and text (support various screen densities)
		            int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
		            tv.setCompoundDrawablePadding(dp5);

		            return v;
		        }
		    };

		new AlertDialog.Builder(this)
		    .setTitle(getString(R.string.siguenostwitter))
		    .setAdapter(adapter, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int item) {
		        	Intent intent = new Intent();
			        intent.setAction(Intent.ACTION_VIEW);
			        intent.addCategory(Intent.CATEGORY_BROWSABLE);
			        intent.setData(Uri.parse(getResources().getString(R.string.twitterurl)+items[item].getTwitterU()));
			        startActivity(intent);
		        }
		    }).show();
 	}
 	
 	/**
 	 * Clase para pintar los itmes de siguenos en twitter
 	 * @author @cgranadax
 	 * Fuente: http://stackoverflow.com/questions/3920640/how-to-add-icon-in-alert-dialog-before-each-item
 	 */
 	public static class SiguenosItem{
 	    public final String text;
 	    public final int icon;
 	    public SiguenosItem(String text, Integer icon) {
 	        this.text = text;
 	        this.icon = icon;
 	    }
 	    @Override
 	    public String toString() {
 	        return text;
 	    }
 	    
 	    public String getTwitterU(){
 	    	return this.text.replace("@", "");
 	    }
 	}
}


