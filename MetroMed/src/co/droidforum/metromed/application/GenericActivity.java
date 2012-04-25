package co.droidforum.metromed.application;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import co.droidforum.metromed.R;
import co.droidforum.metromed.activities.commons.DashboardMainActivity;

public abstract class GenericActivity extends Activity {
	
	private ImageView galaxyLogoImg;
	private ImageView metroMedLogoImg;
	
	
	/**
	 * Parametro que coniente la actividad actual
	 */
	private static GenericActivity actualActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		actualActivity = this;
	}
	
	/**
	 * Rertonar un la actividad actual de tipo GenericActivity
	 * @author Carlos Andres Granada | @cgranadax
	 * @return
	 */
	public static GenericActivity getActualActivity(){
		return actualActivity;
	}
	
	//Para cargar los xmls despues que se crea la actividad y programar toda la navegabilidad al home
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {

		super.onPostCreate(savedInstanceState);
		
		//para hacer navegable a la pagina de GalaxyMovil
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
		
		//para hacer navegable al home cuando no esta ubicado en el home
        metroMedLogoImg = (ImageView)findViewById(R.id.metromedlogoimg);
        //Logo Metro Med
		metroMedLogoImg.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View v){
		    	if(!getActualActivity().getClass().equals(DashboardMainActivity.class) ){
		    		Intent intent = new Intent(AplicationContext.getContextApp(), DashboardMainActivity.class);
			        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			        startActivity(intent);
		    	}
		    }
		});
	}
}
