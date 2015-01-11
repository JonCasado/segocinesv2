package com.segocines;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;


/*************************************************************/
/** @Author = ("Joaquin Casas", "Jon Casado")				**/
/*************************************************************/
///////////////////////////////////////////////////////////////
/* Activity que muestra la cartelera de los cines			 *
 * Luz de Castilla. 										 */
///////////////////////////////////////////////////////////////
public class LuzCastilla extends ActionBar
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
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);			//permite volver a la pantalla principal
        
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
	
		cursor = BD.leerDatos();		//lee los datos de la BD
		startManagingCursor(cursor);
	
		//Muestra los datos obtenidos en FROM, en formato_lista_luzcastilla.xml
		adapter = new SimpleCursorAdapter(this, R.layout.formato_lista_luzcastilla, cursor, FROM, TO);
		listLuzCastilla.setAdapter(adapter);
		listLuzCastilla.setOnItemClickListener(new OnItemClickListener()
		{
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			  {
				  Intent intent = new Intent(LuzCastilla.this, Pelicula.class);
				  startActivity(intent);
			  }
		});
	}
}
