package com.segocines;

import android.app.Application;
import android.content.ContentValues;
import android.util.Log;

public class ApplicationSegoCines extends Application
{
	private static final String TAG = ApplicationSegoCines.class.getSimpleName();
	
	public BaseDeDatos segocinesData;
	
	//Creamos el método getStatusData que nos devuelve el objeto
	//BaseDeDatos si existe (y si no existe lo crea).
	public BaseDeDatos getStatusData()
	{
		if (segocinesData == null)
		{
			segocinesData = new BaseDeDatos(this);
		}
	
		return segocinesData;
	}
	
	
	public synchronized void escribirDatos(int idPeli, String imgPreviaPeli, String imgPeli, String nombrePeli, String nombreOrigPeli, String sinopsisPeli, int edadPeli, String horarioArtesietePeli, String horarioLuzCastillaPeli, String directorPeli, int añoPeli, String paisPeli, int duracionPeli, String generoPeli, String trailerPeli)
	{
		Log.d(TAG, "Añadiendo peliculas");
		
		try
		{
			ContentValues values = new ContentValues();
		
				values.clear();
				values.put(BaseDeDatos.C_ID, idPeli);
				values.put(BaseDeDatos.C_IMGPREVIA, imgPreviaPeli);
				values.put(BaseDeDatos.C_NOMBRE, nombrePeli);
				
			this.getStatusData().insertOrIgnore(values);
			
		} 
		catch (RuntimeException e)
		{
			Log.e(TAG, "Failed to fetch status updates", e);
		}
		
		segocinesData.close();		
	}
}
