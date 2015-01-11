package com.segocines;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.VideoView;

/*************************************************************/
/** @Author = ("Joaquin Casas", "Jon Casado")				**/
/*************************************************************/
///////////////////////////////////////////////////////////////
/* Activity que muestra la informacion de la pelicula		 */
/* seleccionada.											 */
///////////////////////////////////////////////////////////////
public class Pelicula extends ActionBarActivity
{	
	ListView listPeli;
	Cursor cursor;
	SimpleCursorAdapter adapter;

	static final String[] FROM = {BaseDeDatos.C_NOMBRE, BaseDeDatos.C_NOMBREORIG, BaseDeDatos.C_ANYO, BaseDeDatos.C_DURACION, BaseDeDatos.C_PAIS, BaseDeDatos.C_GENERO, BaseDeDatos.C_EDAD, BaseDeDatos.C_SINOPSIS, BaseDeDatos.C_HORARIOARTESIETE};
	static final int[] TO = {R.id.nombrePeli, R.id.nombreOrigPeli, R.id.anyoPeli,  R.id.duracionPeli, R.id.paisPeli, R.id.generoPeli, R.id.edadPeli, R.id.sinopsisPeli, R.id.horarioPeli};
	private static BaseDeDatos BD;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peli);  
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        listPeli = (ListView) findViewById(R.id.listPeli);
        
        /*VideoView mVideoView =(VideoView)findViewById(R.id.trailer);
        mVideoView.setVideoURI(Uri.parse(BaseDeDatos.C_TRAILER));
        mVideoView.start();
        mVideoView.requestFocus();*/

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

		adapter = new SimpleCursorAdapter(this, R.layout.formato_lista_peli, cursor, FROM, TO);
		listPeli.setAdapter(adapter);
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
