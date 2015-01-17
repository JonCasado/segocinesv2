package com.segocines.app;

import java.util.Locale;

import com.segocines.bd.BaseDeDatos;

import android.app.Application;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;


/*************************************************************/
/** @Author = ("Joaquin Casas", "Jon Casado")				**/
/*************************************************************/
///////////////////////////////////////////////////////////////
/* Activity que muestra la cartelera de los cines Artesiete. */
///////////////////////////////////////////////////////////////
public class ApplicationSegoCines extends Application implements OnSharedPreferenceChangeListener
{
	private static final String TAG = ApplicationSegoCines.class.getSimpleName();
	
	public BaseDeDatos segocinesData;	//Base de Datos
    private SharedPreferences prefs;
    private static ApplicationSegoCines appInstance;
    
    public static synchronized ApplicationSegoCines getInstance()
    {
        return appInstance;
    }
    
	
	///////////////////////////////////////////////////////////////
	/* Devuelve el objeto BaseDeDatos.							 */
	///////////////////////////////////////////////////////////////
	public BaseDeDatos getStatusData()
	{
		segocinesData = new BaseDeDatos(this);
	
		return segocinesData;
	}
	
	
	@Override

	public void onCreate()
	{
		super.onCreate();		

		this.setPrefs(PreferenceManager.getDefaultSharedPreferences(this));
		this.getPrefs().registerOnSharedPreferenceChangeListener(this);
	}
	
	
	///////////////////////////////////////////////////////////////
	/* Inserta en la BD los datos de las peliculas.				 */
	///////////////////////////////////////////////////////////////
	public synchronized void escribirDatos(int idPeli, String imgPreviaPeli, String imgPeli, String nombrePeli, String nombreOrigPeli, String sinopsisPeli, String edadPeli, String horarioArtesietePeli, String horarioLuzCastillaPeli, String directorPeli, int anyoPeli, String paisPeli, int duracionPeli, String generoPeli, String trailerPeli)
	{		
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
	//FIN-escribirDatos
	
	
	//Cambia el idioma de la aplicacion
	public void updateLanguage(String idioma)
	{
		Locale myLocale = new Locale(idioma);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        onConfigurationChanged(conf);
    }


	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
	{
		updateLanguage(sharedPreferences.getString("idioma", ""));
	}

	public SharedPreferences getPrefs()
	{
		return prefs;
	}

	public void setPrefs(SharedPreferences prefs)
	{
		this.prefs = prefs;
	}
}
