package com.segocines;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


/*************************************************************/
/** @Author = ("Joaquin Casas", "Jon Casado")				**/
/*************************************************************/
///////////////////////////////////////////////////////////////
/* Activity que muestra la cartelera de los cines			 *
 * Luz de Castilla. 										 */
///////////////////////////////////////////////////////////////
public class LuzCastilla extends ActionBarActivity
{	
	ListView listLuzCastilla;
	Cursor cursor;
	SimpleCursorAdapter adapter;

	static final String[] FROM = {BaseDeDatos.C_NOMBRE, BaseDeDatos.C_DURACION, BaseDeDatos.C_PAIS, BaseDeDatos.C_EDAD, BaseDeDatos.C_HORARIOLUZCASTILLA};
	static final int[] TO = {R.id.nombrePeli, R.id.duracionPeli, R.id.paisPeli, R.id.edadPeli, R.id.horarioPeli};
	private static BaseDeDatos BD;
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luzcastilla);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
		listLuzCastilla = (ListView) findViewById(R.id.listLuzCastilla);
	
	    BD = new BaseDeDatos(this);
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	
		BD.close();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onResume()
	{
		super.onResume();
	
		cursor = BD.leerDatos();
		startManagingCursor(cursor);
	
		adapter = new SimpleCursorAdapter(this, R.layout.formato_lista_luzcastilla, cursor, FROM, TO);
		listLuzCastilla.setAdapter(adapter);
	}
	
	//MENUACTIONBAR
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{			
		switch(item.getItemId())
		{
			case R.id.action_Settings:
				NavUtils.navigateUpFromSameTask(this);
				startActivity(new Intent(this, PrefsActivity.class));
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	//FIN-MENUACTIONBAR
}
