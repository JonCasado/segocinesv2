package com.segocines;

import android.app.Application;
import android.content.ContentValues;
import android.util.Log;

public class ApplicationSegoCines extends Application {
	
	
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
	
	
	public synchronized void escribirDatos(int idPeli, String nombrePeli, String directorPeli, double precio) {
		
		Log.d(TAG, "Añadiendo peliculas");
		
		
		try {
		
			ContentValues values = new ContentValues();
		
				values.clear();
				values.put(BaseDeDatos.C_ID, idPeli);
				values.put(BaseDeDatos.C_NOMBRE, nombrePeli);
				values.put(BaseDeDatos.C_DIRECTOR, directorPeli);
				values.put(BaseDeDatos.C_PRECIO, precio);
				
			this.getStatusData().insertOrIgnore(values);
			
		} 
		catch (RuntimeException e) {
		
			Log.e(TAG, "Failed to fetch status updates", e);
		}
		
		segocinesData.close();
		
		
	}
}
