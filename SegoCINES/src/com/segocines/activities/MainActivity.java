package com.segocines.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.utils.IoUtils;
import com.segocines.R;
import com.segocines.adapter.DrawerAdapter;
import com.segocines.adapter.ImageCursorAdapter;
import com.segocines.app.ApplicationSegoCines;
import com.segocines.bd.BaseDeDatos;
import com.segocines.model.DrawerItem;
import com.segocines.util.JSONParser;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


/*************************************************************/
/** @Author = ("Joaquin Casas", "Jon Casado")				**/
/*************************************************************/
///////////////////////////////////////////////////////////////
/* Activity principal que muestra todas las peliculas		 */
/* de la cartelera de los cines de Segovia.					 */
///////////////////////////////////////////////////////////////
public class MainActivity extends ActionBarActivity
{
	//Objeto Aplication
	private ApplicationSegoCines appSegoCines;
	
	//Descargar imagenes
	private ImageLoaderConfiguration config;
	
	//NavigationDrawer
	private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerAdapter adapter;
    private List<DrawerItem> dataList;
    
    //ListView peliculas
    ListView pelisList;
    Cursor pelisCursor;
	SimpleCursorAdapter pelisAdapter;
    Button btnYT;
	
	//Base de Datos
    private static BaseDeDatos BD;
	static final String[] FROM = {BaseDeDatos.C_IMG, BaseDeDatos.C_NOMBRE, BaseDeDatos.C_HORARIOARTESIETE, BaseDeDatos.C_HORARIOLUZCASTILLA};
	static final int[] TO = {R.id.imgPeli, R.id.nombrePeli, R.id.horarioAPeli, R.id.horarioBPeli};
	
    //URL de donde recogemos la informacion de las peliculas en formato JSON
    private static String url = "http://www.camaradesegovia.es/AutomaticApiRest/getData.php?t=pelicula&c=id_peli,imgPreviaPeli,imgPeli,nombrePeli,nombreOrigPeli,sinopsisPeli,edadPeli,horarioArtesietePeli,horarioLuzCastillaPeli,directorPeli,anyoPeli,paisPeli,duracionPeli,generoPeli,trailerPeli";
    
    //Nombre de los nodos del JSON
    private static final String TAG_DATA = "data";
    private static final String TAG_ID = "id_peli";
    private static final String TAG_IMGPREVIA = "imgPreviaPeli";
    private static final String TAG_IMG = "imgPeli";
    private static final String TAG_NOMBRE = "nombrePeli";
    private static final String TAG_NOMBREORIG = "nombreOrigPeli";
    private static final String TAG_SINOPSIS = "sinopsisPeli";
    private static final String TAG_EDAD = "edadPeli";
    private static final String TAG_HORARIOARTESIETE = "horarioArtesietePeli";
    private static final String TAG_HORARIOLUZCASTILLA = "horarioLuzCastillaPeli";
    private static final String TAG_DIRECTOR = "directorPeli";
    private static final String TAG_ANYO = "anyoPeli";
    private static final String TAG_DURACION = "duracionPeli";
    private static final String TAG_PAIS = "paisPeli";
    private static final String TAG_GENERO = "generoPeli";
    private static final String TAG_TRAILER = "trailerPeli";
    
    JSONArray data = null;
	
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
              
        appSegoCines = ((ApplicationSegoCines) getApplication());
        config = new ImageLoaderConfiguration.Builder(this).build();
	    if (appSegoCines.checkDB())
	    {
	    	//recoge las peliculas si se inicia por primera vez la aplicacion 
	    	if(verificaConexion(this))
	    	{
	    		new JSONParse().execute();
	    	}
	    	else
	    	{
	    		if(!verificaConexion(this))
	    		{
	    		    Toast.makeText(getBaseContext(), getResources().getString(R.string.conex_Error), Toast.LENGTH_SHORT).show();
	    		}
	    	}
	    }
      	
        //Panel DrawerNavigation
        this.dataList = new ArrayList<DrawerItem>();
      	this.drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
      		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
      	this.drawerList = (ListView)findViewById(R.id.left_drawer); //lista de items del panel
	      	dataList.add(new DrawerItem(getResources().getString(R.string.cineArtesiete), R.drawable.ic_action_artesiete));
	      	dataList.add(new DrawerItem(getResources().getString(R.string.cineLuzCastilla), R.drawable.ic_action_luzcastilla));
	      	dataList.add(new DrawerItem(getResources().getString(R.string.nav_settings), R.drawable.ic_action_settingss));
	      	dataList.add(new DrawerItem(getResources().getString(R.string.nav_help), R.drawable.ic_action_help));
      	
      	//Añade los items al Adapter
        this.adapter = new DrawerAdapter(this, R.layout.custom_drawer_item, dataList);
	        drawerList.setAdapter(adapter);
	        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        
	    //Gestiona la apertura y cierre del DrawerNavigation
        this.drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close)
        {
	    	@Override
			public void onDrawerClosed(View view)
	    	{	            
	            supportInvalidateOptionsMenu();
	        }
	 
            @Override
			public void onDrawerOpened(View drawerView)
            {
                supportInvalidateOptionsMenu();
            }
        };
		
			drawerLayout.setDrawerListener(drawerToggle);
			
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);	//añade el boton de volver, al ActionBar
    }
    //FIN-onCreate
    
    
	///////////////////////////////////////////////////////////////
	/* Verifica si se tiene conexion a Internet.				 */
	///////////////////////////////////////////////////////////////
    public static boolean verificaConexion(Context ctx)
    {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        
        for(int i = 0; i < 2; i++)
        {
            if (redes[i].getState() == NetworkInfo.State.CONNECTED)
            {
                bConectado = true; //hay conexion
            }
        }
        return bConectado;
    }
    //FIN-verificarConexion
    
    
	///////////////////////////////////////////////////////////////
	/* Muestra las peliculas en un ListView.					 */
	///////////////////////////////////////////////////////////////
    @SuppressLint("RtlHardcoded")
	@Override
	protected void onResume()
	{
		super.onResume();
		
		drawerLayout.closeDrawer(Gravity.LEFT);	//oculta el NavigationDrawer si estaba abierto

		mostrarPelis();		  
	}
    //FIN-onResume
    
    
	///////////////////////////////////////////////////////////////
	/* Recoge las peliculas de la BD.							 */
	///////////////////////////////////////////////////////////////
    @SuppressWarnings("deprecation")
	public void mostrarPelis()
    {
    	pelisList = (ListView) findViewById(R.id.list);

        BD = new BaseDeDatos(this);

		pelisCursor = BD.leerDatos();
		startManagingCursor(pelisCursor);
		
		pelisAdapter = new ImageCursorAdapter(this, R.layout.formato_lista, pelisCursor, FROM, TO);
		
		pelisList.setAdapter(pelisAdapter);
		pelisList.setOnItemClickListener(new OnItemClickListener()
		{
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			  {
				  Intent intent = new Intent(MainActivity.this, PeliculaActivity.class);
				  intent.putExtra("id_peli", ""+id); //ID de la pelicula seleccionada
				  
				  
				  startActivity(intent);
			  }
		});
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        drawerToggle.syncState();
    }
    
    
	///////////////////////////////////////////////////////////////
	/* Indica el evento del boton de "Atras".					 */
	///////////////////////////////////////////////////////////////
    @SuppressLint("RtlHardcoded")
	@Override
	public void onBackPressed()
    {
    	if(drawerLayout.isDrawerOpen(Gravity.START))
    	{
    		drawerLayout.closeDrawer(Gravity.LEFT);
    	}
    	else
    	{
    		finish();
    	}
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menumain, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
    public void onConfigurationChanged(Configuration newConfig)
	{
        super.onConfigurationChanged(newConfig);
        
        drawerToggle.onConfigurationChanged(newConfig);
    }
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{		
		if(drawerToggle.onOptionsItemSelected(item))
		{
            return true;
        }
		
		switch(item.getItemId())
		{
			//BACK
			case R.id.home:
				finish();
				return true;
			//AJUSTES
			case R.id.action_Settings:
				Intent intent = new Intent(this, PrefsActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				return true;
				
			//ACTUALIZAR
			case R.id.action_Upgrade:
				if(verificaConexion(this))
		    	{
					appSegoCines.eliminarDatos();
		    		new JSONParse().execute();
		    	}
		    	else
		    	{
		    		if(!verificaConexion(this))
		    		{
		    		    Toast.makeText(getBaseContext(), getResources().getString(R.string.conex_Error), Toast.LENGTH_SHORT).show();
		    		}
		    	}
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
		}
	}	
	
	
	///////////////////////////////////////////////////////////////
	/* Indica los eventos de los elementos del panel			 */
	/* NavigationDrawer.										 */
	///////////////////////////////////////////////////////////////
	private class DrawerItemClickListener implements ListView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			Intent intent;
			
			switch(position)
			{
				//ARTESIETE
				case 0:
					intent = new Intent(MainActivity.this, CineActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("horario", "horarioArtesietePeli");
		            startActivity(intent);
					break;
					
				//LUZ DE CASTILLA
				case 1:
					intent = new Intent(MainActivity.this, CineActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("horario", "horarioLuzCastillaPeli");
		            startActivity(intent);
					break;
					
				//AJUSTES
				case 2:
					intent = new Intent(MainActivity.this, PrefsActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		            startActivity(intent);
					break;
					
				//AYUDA
				case 3:
					help();
					break;
					
				default:
					drawerLayout.closeDrawer(drawerList);
					break;
			}
		}
	}
	
	
	///////////////////////////////////////////////////////////////
	/* Muestra una pequena ayuda de utilizacion.				 */
	///////////////////////////////////////////////////////////////
	public void help()
	{
        AlertDialog.Builder faq = new AlertDialog.Builder(this);
        
        faq.setTitle(getResources().getString(R.string.faq_title));
        faq.setMessage(getResources().getString(R.string.faq_text));
         
        faq.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface arg0, int arg1){}
        });
        
        faq.show();
	}
	//FIN-help
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	/* Tarea asincrona que trata de recoger los datos de la direccion dada a traves de pulsar un boton,  */
	/* ademas llamamos al proceso en Background que conseguira el JSON alojado en la url que le pasemos. */
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	private class JSONParse extends AsyncTask<String, String, JSONObject>
	{
		private ProgressDialog pDialog;
	      
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
	            
	        pDialog = new ProgressDialog(MainActivity.this);   
	        pDialog.setMessage(getResources().getString(R.string.dlg_datos));
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(true);
	        pDialog.show();  
	        	        
	        ImageLoader.getInstance().init(config);
		}
		
	    @Override
	    protected JSONObject doInBackground(String... args)
	    {
	    	JSONParser jParser = new JSONParser();
	    	
	        JSONObject json = jParser.getJSONFromUrl(url);	//JSON de la url establecida
	        
	        File storage = Environment.getExternalStorageDirectory();
    		File dir = new File(storage.getAbsoluteFile()+"/segocines");
    		
    		//Elimina las imagenes para descargar las nuevas
    		if(dir.exists())
    		{
		        String deleteCmd = "rm -r "+dir;
		        Runtime runtime = Runtime.getRuntime();
		        try
		        {
		            runtime.exec(deleteCmd);
		        } catch(IOException e){}
    		}
	        
	        try
	    	{           
	    		data = json.getJSONArray(TAG_DATA);			//JSON en forma de Array
          
	    		for(int i = 0; i < data.length(); i++)
	    		{
	    			JSONObject c = data.getJSONObject(i);
	                
	    			// Guardando el  JSON en una Variable
	    			int idPeli = c.getInt(TAG_ID);
	    			String imgPreviaPeli = c.getString(TAG_IMGPREVIA);
	    			String nombrePeli = c.getString(TAG_NOMBRE);
	    			String imgPeli = c.getString(TAG_IMG);
    				try
    				{
						getImageFromUrl(imgPeli, nombrePeli.toLowerCase()+".jpg");
					}
    				catch(IOException e) {e.printStackTrace();}
    				imgPeli = nombrePeli.toLowerCase()+".jpg";
	    			
	    			String nombreOrigPeli = c.getString(TAG_NOMBREORIG);
	    			String sinopsisPeli = c.getString(TAG_SINOPSIS);
	    			String edadPeli = c.getString(TAG_EDAD);
	    			String horarioArtesietePeli = c.getString(TAG_HORARIOARTESIETE);
	    			String horarioLuzCastillaPeli = c.getString(TAG_HORARIOLUZCASTILLA);
	    			String directorPeli = c.getString(TAG_DIRECTOR);
	    			int anyoPeli = c.getInt(TAG_ANYO);
	    			String paisPeli = c.getString(TAG_PAIS);
	    			int duracionPeli = c.getInt(TAG_DURACION);
	    			String generoPeli = c.getString(TAG_GENERO);
	    			String trailerPeli = c.getString(TAG_TRAILER);
	    			
	    			//Almacenando el JSON en la base de datos
	    			// Iteramos sobre todos los componentes de timeline
	    			// Insertar en la base de datos mediante el método escrito en el application
	    			
	    			appSegoCines = ((ApplicationSegoCines) getApplication());
	    			appSegoCines.escribirDatos(idPeli, imgPreviaPeli, imgPeli, nombrePeli, nombreOrigPeli, sinopsisPeli, edadPeli, horarioArtesietePeli, horarioLuzCastillaPeli, directorPeli, anyoPeli, paisPeli, duracionPeli, generoPeli, trailerPeli);
	    		}
	    		//FIN-for
       
	    	} catch (JSONException e) {e.printStackTrace();}
	        
	        return json;
	    }
	    
	    @Override
        protected void onPostExecute(JSONObject json)
	    {
	    	pDialog.dismiss();
	    	MainActivity.this.onResume();
	    }
	    //FIN-onPostExecute
	}
	//FIN-JSONParse
	
	
	/////////////////////////////////////////////////////////////////
	/* Descarga una imagen desde una URL y la guarda en la SDcard. */
	/////////////////////////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	private boolean getImageFromUrl(String urlStr, String name) throws IOException
	{
		boolean saved = false;
		
		checkExternalMedia();
		
		File storage = Environment.getExternalStorageDirectory();
		File dir = new File(storage.getAbsoluteFile()+"/segocines");
		
		if(!dir.exists())
		{
			dir.mkdir();
		}
		
		File file = new File(dir, name);
		
		InputStream is = null;
		
		File cachedImage = ImageLoader.getInstance().getDiscCache().get(urlStr);
		if(cachedImage != null && cachedImage.exists()) //existe
		{
			is = new FileInputStream(cachedImage);
		}
		else //descargar
		{
			ImageDownloader downloader = new BaseImageDownloader(this);
			is = downloader.getStream(urlStr, null);
		}
		
		if(is != null)
		{
	        try
	        {
	            FileOutputStream targetStream = new FileOutputStream(file);
	            try
	            {
	                IoUtils.copyStream(is, targetStream, null);
	                saved = true;
	            }
	            finally
	            {
	                targetStream.close();
	            }
	        }
	        finally
	        {
	            is.close();
	        }
	    }
		
		return saved;
	}
	//FIN-getImageFromUrl
	
	
	///////////////////////////////////////////////////////////////
	/* Comprueba si hay acceso a la SDcard.						 */
	///////////////////////////////////////////////////////////////
	private void checkExternalMedia()
	{
		boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            // Can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        }
        else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
        {
            // Can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        }
        else
        {
            // Can't read or write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        Log.i("CHECK", "\n\nExternal Media: readable ="+mExternalStorageAvailable+" writable = "+mExternalStorageWriteable);
    }
	//FIN-checkExternalMedia
}