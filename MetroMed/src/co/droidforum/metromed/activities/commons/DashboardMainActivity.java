package co.droidforum.metromed.activities.commons;

/**
 * Clase encargada de construir el menu principal y de determinar el comportamiento de
 * los botones que la contienen
 * @author DroidForum.co / GalaxyMovil.com / @cgranadax
 */

import co.droidforum.metromed.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DashboardMainActivity extends Activity {
	/*
	 * Elementos de la pantalla
	 */
	private ImageView galaxyLogoImg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_main);
		
		/*
		 * Programando los eventos de cada elemento de la pantalla 
		 */
		galaxyLogoImg = (ImageView)findViewById(R.id.galaxylogoimg);
		galaxyLogoImg.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View v){
		        Intent intent = new Intent();
		        intent.setAction(Intent.ACTION_VIEW);
		        intent.addCategory(Intent.CATEGORY_BROWSABLE);
		        intent.setData(Uri.parse("http://www.galaxymovil.com"));
		        startActivity(intent);
		    }
		});
	}
}
