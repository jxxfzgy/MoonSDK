package com.j256.ormlite.android.compat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Compatibility interface to support various different versions of the Android API.
 * 
 * @author graywatson
 */
public interface ApiCompatibility {

	/**
	 * Perform a raw query on a database with an optional cancellation-hook.
	 */
	public Cursor rawQuery(SQLiteDatabase db, String sql, String[] selectionArgs, CancellationHook cancellationHook);

	/**
	 * Return a cancellation hook object that will be passed to the
	 * {@link #rawQuery(android.database.sqlite.SQLiteDatabase, String, String[], com.j256.ormlite.android.compat.ApiCompatibility.CancellationHook)}. If not supported then this will return
	 * null.
	 */
	public CancellationHook createCancellationHook();

	/**
	 * Cancellation hook class returned by {@link com.j256.ormlite.android.compat.ApiCompatibility#createCancellationHook()}.
	 */
	public interface CancellationHook {
		/**
		 * Cancel the associated query.
		 */
		public void cancel();
	}
}
