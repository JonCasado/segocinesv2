package com.segocines;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.segocines.adapter.CustomListAdapter;
import com.segocines.adapter.DrawerAdapter;
import com.segocines.app.ApplicationSegoCines;
import com.segocines.model.Pelicula;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


/*************************************************************/
/** @Author = ("Joaquin Casas", "Jon Casado")				**/
/*************************************************************/
///////////////////////////////////////////////////////////////
/* Activity principal que muestra todas las peliculas		 */
/* de la cartelera de los cines de Segovia.					 */
///////////////////////////////////////////////////////////////
public class MainActivity extends ActionBarActivity implements OnSharedPreferenceChangeListener
{
	//Preferencias Compartidas
	SharedPreferences prefs;
	
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
    CustomListAdapter pelisCustomAdapter;
    List<Pelicula> movieList = new ArrayList<Pelicula>();
	//private ImageDownloader imgDown;
	
	//Base de Datos
	static final String[] FROM = {BaseDeDatos.C_NOMBRE, BaseDeDatos.C_HORARIOARTESIETE, BaseDeDatos.C_HORARIOLUZCASTILLA};
	static final int[] TO = {R.id.nombrePeli, R.id.horarioAPeli, R.id.horarioBPeli};
	private static BaseDeDatos BD;
	public ApplicationSegoCines appSegoCines;
    
    //ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
    
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
	
    
	///////////////////////////////////////////////////////////////
	/* - Preferencias compartidas.								 */
    /* - NavigationDrawer.										 */
	///////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Preferencias compartidas
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
      	
        //Panel DrawerNavigation
        this.dataList = new ArrayList<DrawerItem>();
      	this.drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
      		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
      	this.drawerList = (ListView)findViewById(R.id.left_drawer); //lista de items del panel
	      	dataList.add(new DrawerItem("Artesiete", R.drawable.ic_action_artesiete));
	      	dataList.add(new DrawerItem("Luz de Castilla", R.drawable.ic_action_luzcastilla));
	      	dataList.add(new DrawerItem("Ajustes", R.drawable.ic_action_settingss));
	      	dataList.add(new DrawerItem("Ayuda", R.drawable.ic_action_help));
      	
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
		//getSupportActionBar().setHomeButtonEnabled(true);
    }
    //FIN-onCreate
    
    
	///////////////////////////////////////////////////////////////
	/* Muestra las peliculas en un ListView.					 */
	///////////////////////////////////////////////////////////////
    @SuppressLint("RtlHardcoded")
	@SuppressWarnings("deprecation")
	@Override
	protected void onResume()
	{
		super.onResume();
		
		drawerLayout.closeDrawer(Gravity.LEFT);	//oculta el NavigationDrawer si estaba abierto

		pelisList = (ListView) findViewById(R.id.list);

        BD = new BaseDeDatos(this);

		pelisCursor = BD.leerDatos();
		startManagingCursor(pelisCursor);
		
		pelisAdapter = new SimpleCursorAdapter(this, R.layout.formato_lista, pelisCursor, FROM, TO);
		
		//pelisAdapter = new CustomListAdapter(this, movieList);
		
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
    //FIN-onResume
    
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
    		super.onBackPressed();
    	}
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu, menu);
		
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
			//AJUSTES
			case R.id.action_Settings:
				startActivity(new Intent(this, PrefsActivity.class));
				return true;
				
			//ACTUALIZAR
			case R.id.action_Upgrade:
				new JSONParse().execute();
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
	{
		Intent intent = new Intent(MainActivity.this, PrefsActivity.class);
        startActivity(intent);
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
					intent = new Intent(MainActivity.this, ArteSiete.class);
		            startActivity(intent);
					break;
					
				//LUZ DE CASTILLA
				case 1:
					intent = new Intent(MainActivity.this, LuzCastilla.class);
		            startActivity(intent);
					break;
					
				//AJUSTES
				case 2:
					intent = new Intent(MainActivity.this, PrefsActivity.class);
		            startActivity(intent);
					break;
					
				//AYUDA
				case 3:
					//mensaje de F.A.Q.
					break;
					
				default:
					drawerLayout.closeDrawer(drawerList);
					break;
			}
		}
	}
	
	
	//Tarea asincrona que trata de recoger los datos de la direccion dada a traves de pulsar un boton,
	//ademas llamamos al proceso en Background que conseguira el JSON alojado en la url que le pasemos.
	private class JSONParse extends AsyncTask<String, String, JSONObject>
	{
		private ProgressDialog pDialog;
	      
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
	            
	        pDialog = new ProgressDialog(MainActivity.this);   
	        pDialog.setMessage("Recogiendo datos...");
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(true);
	        pDialog.show();  
		}
		
	    @Override
	    protected JSONObject doInBackground(String... args)
	    {
	    	JSONParser jParser = new JSONParser();
	    	
	        JSONObject json = jParser.getJSONFromUrl(url); //JSON de la url establecida
	        return json;
	    }
	    
	    @Override
        protected void onPostExecute(JSONObject json)
	    {
	    	pDialog.dismiss();
        
	    	try
	    	{           
	    		data = json.getJSONArray(TAG_DATA);		//JSON en forma de Array
          
	    		for(int i = 0; i < data.length(); i++)
	    		{
	    			JSONObject c = data.getJSONObject(i);
	                
	    			// Guardando el  JSON en una Variable
	    			int idPeli = c.getInt(TAG_ID);
	    			String imgPreviaPeli = c.getString(TAG_IMGPREVIA);
	    			String imgPeli = c.getString(TAG_IMG);
	    			String nombrePeli = c.getString(TAG_NOMBRE);
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
	    			
	    			// Añadiendo valores al HashMap, de forma clave => valor
	    			/*HashMap<String, String> map = new HashMap<String, String>();
	                
	    			map.put(TAG_NOMBRE, nombrePeli);
	    			map.put(TAG_HORARIOARTESIETE, horarioArtesietePeli);
	                
	    			oslist.add(map);
	    			
	    			pelisList = (ListView)findViewById(R.id.list);
	                
	    			ListAdapter adapter = new SimpleAdapter(MainActivity.this, oslist, R.layout.formato_lista,
	    				new String[] {TAG_NOMBRE, TAG_HORARIOARTESIETE},
	    				new int[] {R.id.nombrePeli, R.id.horarioAPeli}
	    			);
	                
	    			pelisList.setAdapter(adapter);
	                
	    			pelisList.setOnItemClickListener(new AdapterView.OnItemClickListener()
	    			{
	    				@Override
	    				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    				{
	    					Toast.makeText(MainActivity.this, "Has clickado en "+oslist.get(+position).get("nombrePeli"), Toast.LENGTH_SHORT).show();																					
	    				}
	    			});*/
	                
	    			MainActivity.this.onResume();
	    		}
	    		//FIN-for
       
	    	} catch (JSONException e) {e.printStackTrace();}
	    }
	    //FIN-onPostExecute
	}
	//FIN-JSONParse
}