package co.droidforum.metromed.application;

import android.app.Activity;
import android.os.Bundle;

public abstract class GenericActivity extends Activity {
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
}
