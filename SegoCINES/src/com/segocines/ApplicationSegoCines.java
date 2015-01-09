package com.segocines;

import android.app.Application;
import android.content.ContentValues;
import android.util.Log;

public class ApplicationSegoCines extends Application {
	
	/*
	
	private static final String TAG = ApplicationSegoCines.class.getSimpleName();
	
	private BaseDeDatos segocinesData;
	
	
	//Creamos el método getStatusData que nos devuelve el objeto
	//BaseDeDatos si existe (y si no existe lo crea).
	public BaseDeDatos getStatusData() {
	
		if (segocinesData == null) {
	
			segocinesData = new BaseDeDatos(this);
		}
	
		return segocinesData;
	}
	
	public synchronized void fetchStatusUpdates() {
		
		Log.d(TAG, "Fetching status updates");
		
		
		try {
		
		
			ContentValues values = new ContentValues();
		
			for (Twitter.Status status : timeline) {
		
				values.clear();
		
				values.put(BaseDeDatos.C_ID, status.id);
				values.put(BaseDeDatos.C_CREATED_AT, status.createdAt.getTime());
				values.put(BaseDeDatos.C_TEXT, status.text);
				values.put(BaseDeDatos.C_USER, status.user.name);
		
				Log.d(TAG, String.format("%s: %s", status.user.name, status.text));
		
				this.getStatusData().insertOrIgnore(values);
			}
		} 
		catch (RuntimeException e) {
		
			Log.e(TAG, "Failed to fetch status updates", e);
		}
	}
	
	*/
}
