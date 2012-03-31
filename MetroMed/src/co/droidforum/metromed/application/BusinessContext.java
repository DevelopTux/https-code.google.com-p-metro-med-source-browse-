package co.droidforum.metromed.application;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class BusinessContext {

	private static Injector injector;
	private static Map<String, Object> sessionData = new HashMap<String, Object>();
	
	private static Injector getInjector(){
		if(injector == null) {
			injector = Guice.createInjector(new BusinessModule());
		}
		
		return injector;
	}
	
	/**
	 * Obtiene una instancia de la clase business object que
	 * se esta pasando como parametro
	 *  
	 * @param clazz
	 * @return
	 */
	public static <E> E getBean(Class<E> clazz){
		if(clazz.isAnnotationPresent(BusinessObject.class))
			return getInjector().getInstance(clazz);
		
		throw new IllegalArgumentException("Solo se pueden instanciar objetos de negocio!");
	}
	
	/**
	 * Monta en session el objeto que se esta pasando como parametro
	 * identificandolo con el nombre que se pasa en el parametro key
	 * 
	 * @param key
	 * @param data
	 */
	public static void putSessionData(String key, Object data){
		sessionData.put(key, data);
	}
	
	/**
	 * Obtiene el objeto que esta almacenado en session con el
	 * nombre que se pasa como parametro
	 * 
	 * @param key
	 * @return
	 */
	public static Object getSessionData(String key){
		return sessionData.get(key);
	}
	
	/**
	 * Destruye la session de la aplicacion
	 */
	public static void destroySession(){
		sessionData = new HashMap<String, Object>();
	}
	
	/**
	 * Remueve un objeto de session que corresponde al nombre
	 * del key que se pasa como parametro
	 * 
	 * @param key
	 */
	public static void removeSessionData(String key){
		sessionData.remove(key);
	}
}

class BusinessModule extends AbstractModule {

	@Override
	protected void configure() {
	}	
}
