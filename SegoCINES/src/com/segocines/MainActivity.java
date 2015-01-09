package com.segocines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

//MAINACTIVITY
public class MainActivity extends ActionBarActivity implements OnSharedPreferenceChangeListener
{
	SharedPreferences prefs;
	
	private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerAdapter adapter;

    private List<DrawerItem> dataList;
    
    ListView list;
    TextView nombre;
    TextView director;
    TextView precio;
    Button Btngetdata;
    
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
    
    //URL de donde recogemos la informacion de las peliculas en formato JSON
    
    private static String url = "http://www.camaradesegovia.es/AutomaticApiRest/getData.php?t=pelicula&c=id_peli,nombrePeli,directorPeli,precioPeli";
    
    //Nombre de los nodos del JSON
    
    private static final String TAG_DATA = "data";
    private static final String TAG_ID = "id_peli";
    private static final String TAG_NOMBRE = "nombrePeli";
    private static final String TAG_DIRECTOR = "directorPeli";
    private static final String TAG_PRECIO = "precioPeli";
    
    JSONArray data = null;
    
    //Parte del SQLite de la aplicacion, declaracion de variables
    
    public ApplicationSegoCines appSegoCines;
	
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
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);	//añade el boton de volver al ActionBar
		//getSupportActionBar().setHomeButtonEnabled(true);
    	
		//Parte del codigo que obtendra las peliculas a traves de pulsar un boton, ademas llamamos al proceso en Background que 
		//conseguira el JSON alojado en la url que le pasemos.
		//Creamos tambien un hashmap para la posible implementacion de pulsar en una parte del list view y que se abra otro activity.
		
		oslist = new ArrayList<HashMap<String, String>>();
		Btngetdata = (Button)findViewById(R.id.getdata);
		
        Btngetdata.setOnClickListener(new View.OnClickListener()
        {
	        @Override
	        public void onClick(View view)
	        {
	        	new JSONParse().execute();
	        }
        });
        
    }
    
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        drawerToggle.syncState();
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
			case R.id.action_Settings:
				startActivity(new Intent(this, PrefsActivity.class));
				return true;
				/*
			case R.id.action_Upgrade:
				startService(new Intent(this, ServicioRecogerDatos.class));
				return true;
				*/
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
	
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			Intent intent;
			
			switch(position)
			{
				case 0:
					intent = new Intent(MainActivity.this, ArteSiete.class);
		            startActivity(intent);
					break;
					
				case 1:
					intent = new Intent(MainActivity.this, LuzCastilla.class);
		            startActivity(intent);
					break;
					
				case 2:
					intent = new Intent(MainActivity.this, PrefsActivity.class);
		            startActivity(intent);
					break;
					
				case 3:
					//mensaje de F.A.Q.
					break;
					
				default:
					drawerLayout.closeDrawer(drawerList);
					break;
			}
		}
	}
	
	
	//Tarea asincrona que trata de recoger los datos de la direccion dada
	
	private class JSONParse extends AsyncTask<String, String, JSONObject>
	{
		private ProgressDialog pDialog;
	      
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();     
			
			nombre = (TextView)findViewById(R.id.nombrePeli);
			director = (TextView)findViewById(R.id.directorPeli);
	        precio = (TextView)findViewById(R.id.precioPeli);
	            
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
	    			
	    			int idPelicula = c.getInt(TAG_ID);
	    			String nombrePelicula = c.getString(TAG_NOMBRE);
	    			String directorPelicula = c.getString(TAG_DIRECTOR);
	    			double precioPelicula = c.getDouble(TAG_PRECIO);
	    			
	    			//Almacenando el JSON en la base de datos
	    			
	    			
	    			// Iteramos sobre todos los componentes de timeline
	    			
	    			
	    			// Insertar en la base de datos mediante el método escrito en el application
	    			
	    			appSegoCines = ((ApplicationSegoCines) getApplication());
	    			
	    			appSegoCines.escribirDatos(idPelicula, nombrePelicula, directorPelicula, precioPelicula);
	    			
	    			
	    			// Añadiendo valores al HashMap, de forma clave => valor
	    			HashMap<String, String> map = new HashMap<String, String>();
	                
	    			map.put(TAG_NOMBRE, nombrePelicula);
	    			map.put(TAG_DIRECTOR, directorPelicula);
	    			//map.put(TAG_PRECIO, precioPelicula);
	                
	    			oslist.add(map);
	                
	    			list = (ListView)findViewById(R.id.list);
	                
	    			ListAdapter adapter = new SimpleAdapter(MainActivity.this, oslist,R.layout.formato_lista,
	    				new String[] {TAG_NOMBRE,TAG_DIRECTOR/*, TAG_PRECIO*/},
	    				new int[] {R.id.nombrePeli,R.id.directorPeli/*, R.id.precioPeli*/}
	    			);
	                
	    			list.setAdapter(adapter);
	                
	    			list.setOnItemClickListener(new AdapterView.OnItemClickListener()
	    			{
	    				@Override
	    				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    				{
	    					Toast.makeText(MainActivity.this, "Has clickado en "+oslist.get(+position).get("nombrePeli"), Toast.LENGTH_SHORT).show();																					
	    				}
	                    
	    			});
	                
	    		}//FIN-for
       
	    	} catch (JSONException e) {e.printStackTrace();}
	    }
	}
}