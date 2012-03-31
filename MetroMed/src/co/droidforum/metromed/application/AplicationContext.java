package co.droidforum.metromed.application;


import java.io.InputStream;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;

/**
 * 
 * Clase que extiende el funcionamiento de Application para así tener metodos genericos que se encargan de obtener
 * info particular tal como el contexto de la aplicacion dentro de clases que no extiendan de Activity
 * 
 * @author Carlos Daniel Muñoz Idárraga | @cdmunozi
 * @since  30/03/2012
 *
 */
public class AplicationContext extends Application {
	
	private static Context contextApp;
	
	/**
	 * Permite crear las configuraciones de la
	 * aplicacion
	 */
	public void onCreate(){
		super.onCreate();
		contextApp = this.getApplicationContext();
	}
	
	/**
	 * Obtiene el contexto de la aplicacion
	 * @return Objeto Contexto
	 */
	public static Context getContextApp(){
		return contextApp;
	}
	
	/**
	 * Obtiene el valor de un recurso de tipo string
	 * @param idResource	El id del archivo de R
	 * @return	El contenido respectivo del id
	 */
	public static String getValueStringResource(int idResource){
		String value = null;
		Resources resources = contextApp.getResources();
		value = resources.getString(idResource);
		
		return value;
	}
	
	/**
	 * Obtiene el content resolver del contexto
	 * @return el objeto content resolver
	 */
	public static ContentResolver getContentResolverApp(){
		return contextApp.getContentResolver();
	}
	
	/**
	 * Obtiene el servicio del sistema solicitado
	 * @return el objeto servicio solicitado
	 */
	public static Object getSystemServiceApp(String name){
		return contextApp.getSystemService(name);
	}
	
	
	/**
	 * 
     * Checks if the application is in the background (i.e behind another application's Activity).
     * Para ejecutar esta funcion sin errores es necesario crear el siguiente permiso en el manifest
     * <uses-permission android:name="android.permission.GET_TASKS" /> 
     * Fuente: https://github.com/kaeppler/droid-fu
     * @author kaeppler
     * @param context
     * @return true if another application is above this one.
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }

        return false;
    }
	
    /**
	 * Obtiene un raw resource de la aplicacion que corresponde al
	 * id que se pasa como parametro
	 *  
	 * @param idRawResource Id del resource
	 * @return	Stream del archivo
	 */
	public static InputStream getRawResource(int idRawResource) {
		Resources resources = contextApp.getResources();
		InputStream rawResource = resources.openRawResource(idRawResource);
		
		return rawResource;
	}
}