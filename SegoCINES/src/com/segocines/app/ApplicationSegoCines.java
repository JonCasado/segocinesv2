package com.segocines.app;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.segocines.BaseDeDatos;
import com.segocines.util.LruBitmapCache;

import android.app.Application;
import android.content.ContentValues;
import android.text.TextUtils;
import android.util.Log;


/*************************************************************/
/** @Author = ("Joaquin Casas", "Jon Casado")				**/
/*************************************************************/
///////////////////////////////////////////////////////////////
/* Activity que muestra la cartelera de los cines Artesiete. */
///////////////////////////////////////////////////////////////
public class ApplicationSegoCines extends Application
{
	private static final String TAG = ApplicationSegoCines.class.getSimpleName();
	
	public BaseDeDatos segocinesData;	//Base de Datos
	private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    
    public static ApplicationSegoCines appInstance;
    
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
	
	
	public RequestQueue getRequestQueue()
	{
        if (mRequestQueue == null)
        {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
 
        return mRequestQueue;
    }
 
    public ImageLoader getImageLoader()
    {
        getRequestQueue();
        if (mImageLoader == null)
        {
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }
 
    public <T> void addToRequestQueue(Request<T> req, String tag)
    {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
 
    public <T> void addToRequestQueue(Request<T> req)
    {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
 
    public void cancelPendingRequests(Object tag)
    {
        if (mRequestQueue != null)
        {
            mRequestQueue.cancelAll(tag);
        }
    }
}
