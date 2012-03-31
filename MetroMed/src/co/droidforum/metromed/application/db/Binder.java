package co.droidforum.metromed.application.db;

import android.database.Cursor;

public interface Binder<T> {
	public T bind(Cursor cusor);
}
