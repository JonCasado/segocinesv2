package com.segocines;

import android.app.Application;
import android.content.ContentValues;
import android.util.Log;

public class ApplicationSegoCines extends Application
{
	private static final String TAG = ApplicationSegoCines.class.getSimpleName();
	
	public BaseDeDatos segocinesData;
	
	//Creamos el m�todo getStatusData que nos devuelve el objeto
	//BaseDeDatos si existe (y si no existe lo crea).
	public BaseDeDatos getStatusData()
	{
		segocinesData = new BaseDeDatos(this);
	
		return segocinesData;
	}
	
	
	public synchronized void escribirDatos(int idPeli, String imgPreviaPeli, String imgPeli, String nombrePeli, String nombreOrigPeli, String sinopsisPeli, int edadPeli, String horarioArtesietePeli, String horarioLuzCastillaPeli, String directorPeli, int anyoPeli, String paisPeli, int duracionPeli, String generoPeli, String trailerPeli)
	{
		Log.d(TAG, "A�adiendo peliculas");
		
		try
		{
			ContentValues values = new ContentValues();
		
				values.clear();
				values.put(BaseDeDatos.C_ID, idPeli);
				values.put(BaseDeDatos.C_IMGPREVIA, imgPreviaPeli);
				values.put(BaseDeDatos.C_IMG, imgPeli);
				values.put(BaseDeDatos.C_NOMBRE, nombrePeli);
				values.put(BaseDeDatos.C_NOMBREORIG, nombreOrigPeli);
				values.put(BaseDeDatos.C_SINOPSIS, sinopsisPeli);
				values.put(BaseDeDatos.C_EDAD, edadPeli);
				values.put(BaseDeDatos.C_HORARIOARTESIETE, horarioArtesietePeli);
				values.put(BaseDeDatos.C_HORARIOLUZCASTILLA, horarioLuzCastillaPeli);
				values.put(BaseDeDatos.C_DIRECTOR, directorPeli);
				values.put(BaseDeDatos.C_ANYO, anyoPeli);
				values.put(BaseDeDatos.C_PAIS, paisPeli);
				values.put(BaseDeDatos.C_GENERO, generoPeli);
				values.put(BaseDeDatos.C_DURACION, duracionPeli);
				values.put(BaseDeDatos.C_TRAILER, trailerPeli);
							
			this.getStatusData().insertOrIgnore(values);
			
		} 
		catch (RuntimeException e)
		{
			Log.e(TAG, "Failed to fetch status updates", e);
		}
		
		segocinesData.close();		
	}
}
