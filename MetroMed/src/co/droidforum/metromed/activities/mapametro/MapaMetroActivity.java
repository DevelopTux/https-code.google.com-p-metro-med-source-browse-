package co.droidforum.metromed.activities.mapametro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import co.droidforum.metromed.R;
import co.droidforum.metromed.activities.commons.DashboardMainActivity;
import co.droidforum.metromed.application.AplicationContext;

public class MapaMetroActivity extends Activity {
	 WebView mWebView = null;
	 
	 private Button buttonHorariosBottomBar;
	 private Button buttonTarifasBottomBar;
	 private ImageView metroMedLogoImg;
	 private ImageView galaxyLogoImg;
	  
     @Override
     public void onCreate(Bundle savedInstanceState) {

         super.onCreate(savedInstanceState);

         setContentView(R.layout.mapa_metro);
         mWebView = (WebView)findViewById(R.id.webViewMapa);
         mWebView.getSettings().setBuiltInZoomControls(true);
         mWebView.loadDataWithBaseURL("file:///android_asset/", "<img src='mapa_lineas.jpg' width='300' />", "text/html", "utf-8", null);
         
         //obtiene la referencia del boton de horarios y carga un dialogo con la información de horarios al hacer tap
         buttonHorariosBottomBar = (Button)findViewById(R.id.buttonHorariosBottomBar);
         buttonHorariosBottomBar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogButtonHorarios();
			}
		});
        
         //obtiene la referencia del boton de tarifas y carga un dialogo con la información de tarifas al hacer tap
         buttonTarifasBottomBar = (Button)findViewById(R.id.buttonTarifasBottomBar);
         buttonTarifasBottomBar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogButtonTarifas();
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
     
    /**
 	 * Metodo que genera un cuadro de dialogo de alerta con la información de horarios
 	 */
 	private void dialogButtonHorarios() {
 		AlertDialog.Builder builder = new AlertDialog.Builder(this);
 		builder.setCancelable(false)
 		       .setPositiveButton(R.string.alertDialogHorariosAceptar, new DialogInterface.OnClickListener() {
 		           public void onClick(DialogInterface dialog, int id) {
 		                //no hace nada sólo volver a la actividad
 		           }
 		       });
 		AlertDialog alert = builder.create();
 		alert.setTitle(R.string.alertDialogHorariosTitle);
 		alert.setMessage(AplicationContext.getValueStringResource(R.string.alertDialogHorariosText));
 		alert.show();
 		
 	}
    
 	/**
 	 * Metodo que genera un cuadro de dialogo de alerta con la información de tarifas
 	 */
 	private void dialogButtonTarifas() {
 		AlertDialog.Builder builder = new AlertDialog.Builder(this);
 		builder.setCancelable(false)
 		       .setPositiveButton(R.string.alertDialogTarifaAceptar, new DialogInterface.OnClickListener() {
 		           public void onClick(DialogInterface dialog, int id) {
 		                //no hace nada sólo volver a la actividad
 		           }
 		       });
 		AlertDialog alert = builder.create();
 		alert.setTitle(R.string.alertDialogTarifaTitle);
 		alert.setMessage(AplicationContext.getValueStringResource(R.string.alertDialogTarifaText));
 		alert.show();
 		
 	}
     
}
