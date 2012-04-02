package co.droidforum.metromed.application.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * Clase encargada de crear una BD al momento de cargar la app.
 * 
 * @author Carlos Daniel Muñoz Idárraga | @cdmunozi
 * @since  30/03/2012
 *
 */
public class MetroMedDB extends SQLiteOpenHelper{
	private static final String DATABASE = "MetroMedDB.db";
	private static final String sqlCreateTableEstacionesMetro = 
									"CREATE TABLE estaciones_metro (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
																	"nombre TEXT, latitud TEXT, longitud TEXT, " +
																	"linea TEXT, imagen TEXT)";
	
	public MetroMedDB(Context context){
		super(context, DATABASE, null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sqlCreateTableEstacionesMetro);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS estaciones_metro");
		onCreate(db);
	}

}