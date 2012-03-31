package co.droidforum.metromed.activities.commons;

/**
 * Clase encargada de mostrar una imagen cuando se lanza la aplicación y esperar unos segundos determinados
 * antes de ir a la siguiente actividad para mostrar
 * @author DroidForum.co / GalaxyMovil.com / @cgranadax
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import co.droidforum.metromed.R;
import co.droidforum.metromed.application.BusinessContext;
import co.droidforum.metromed.bo.EstacionesMetroBO;

public class SplashAppActivity extends Activity {
	/*
	 * Tiempo de espera del splash en caso de que no se realice ninguna acción durante la carga de 
	 * la aplicación y se desee mostrar una presentación introductoria
	 */
	private final int SPLASH_DISPLAY_LENGTH = 2000;
	
	private EstacionesMetroBO estacionesMetroBO = BusinessContext.getBean(EstacionesMetroBO.class);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_app);
		Handler handler = new Handler();
        handler.postDelayed(getRunnableStartApp(), SPLASH_DISPLAY_LENGTH);
		
	}
	
	/**
	 * Metodo en el cual se debe incluir dentro de run(){Tu codigo} el codigo que se quiere realizar una
	 * vez ha finalizado el tiempo que se desea mostrar la actividad de splash 
	 * @return
	 */
	private Runnable getRunnableStartApp(){
    	Runnable runnable = new Runnable(){
        	public void run(){
        		
        		//inserta las estaciones si no existen
        		if(estacionesMetroBO.getAllEstacionesMetro().size()<=0){
        			EstacionesMetroBO estacionesMetroBO = BusinessContext.getBean(EstacionesMetroBO.class);
        			estacionesMetroBO.insertRecordsEstacionesMetro();
        		}
        		Intent intent = new Intent(SplashAppActivity.this, DashboardMainActivity.class);
        		startActivity(intent);
        		finish();
        	}
    	};
    	
    	return runnable;
	}
}
