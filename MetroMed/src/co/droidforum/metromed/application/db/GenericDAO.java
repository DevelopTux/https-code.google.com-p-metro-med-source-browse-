package co.droidforum.metromed.application.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import co.droidforum.metromed.application.AplicationContext;

/**
 * 
 * Clase que implementa los distintos métodos CRUD de interacción con la BD
 * 
 * @author Carlos Daniel Muñoz Idárraga | @cdmunozi
 * @since  30/03/2012
 *
 */
public class GenericDAO {

	
	protected <E> List<E> getResultsByQuery(String query, Binder<E> binder){
		List<E> results = new ArrayList<E>();
		MetroMedDB autobd = new MetroMedDB(AplicationContext.getContextApp());
		SQLiteDatabase db = autobd.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, new String[] {});
		
		if (cursor.moveToFirst()) {
			do {
				results.add(binder.bind(cursor));
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return results;
	}	
	
	/**
	 * 
	 * Inserta un registro en la BD
	 * 
	 * @param values	La data a insertar
	 * @param table		La tabla en la que se va a insertar
	 */
	protected void insert(ContentValues values, String table){
		MetroMedDB autobd = new MetroMedDB(AplicationContext.getContextApp());
		SQLiteDatabase db = autobd.getWritableDatabase();
		db.insert(table, null, values);
		db.close();
	}
	
	/**
	 * 
	 * Actualiza registros en la BD
	 * 
	 * @param values	La data a actualizar
	 * @param table		La tabla a actualizar
	 * @param whereClause	El where, aca debe ir el id <<No incluye la palabra WHERE>>
	 * @param whereArgs		Puede ser nulo si el id de whereClause ya está seteado
	 */
	protected void update(ContentValues values, String table, String whereClause, String[] whereArgs){
		MetroMedDB autobd = new MetroMedDB(AplicationContext.getContextApp());
		SQLiteDatabase db = autobd.getWritableDatabase();
		db.update(table, values, whereClause, whereArgs);
		db.close();
	}
	
	/**
	 * 
	 * Elimina registros en la BD
	 * 
	 * @param table			La tabla en la que se va a eliminar
	 * @param whereClause	El where, aca debe ir el id <<No incluye la palabra WHERE>>
	 * @param whereArgs		Puede ser nulo si el id de whereClause ya está seteado
	 */
	protected void delete(String table, String whereClause, String[] whereArgs) {
		MetroMedDB autobd = new MetroMedDB(AplicationContext.getContextApp());
		SQLiteDatabase db = autobd.getWritableDatabase();
		db.delete(table, whereClause, whereArgs);
		db.close();
	}
	
}
