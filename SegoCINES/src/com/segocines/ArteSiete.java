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
/* Activity que muestra la cartelera de los cines Artesiete. */
///////////////////////////////////////////////////////////////
public class ArteSiete extends ActionBarActivity
{	
	ListView listArtesiete;
	Cursor cursor;
	SimpleCursorAdapter adapter;

	static final String[] FROM = {BaseDeDatos.C_NOMBRE, BaseDeDatos.C_DIRECTOR};
	static final int[] TO = {R.id.nombrePeli};
	private static BaseDeDatos BD;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artesiete);  
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        listArtesiete = (ListView) findViewById(R.id.listArtesiete);

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

		adapter = new SimpleCursorAdapter(this, R.layout.formato_lista, cursor, FROM, TO);
		listArtesiete.setAdapter(adapter);
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
