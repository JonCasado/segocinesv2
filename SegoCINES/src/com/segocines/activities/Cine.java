package com.segocines.activities;

import com.segocines.R;
import com.segocines.bd.BaseDeDatos;
import com.segocines.model.ActionBar;

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
/* Activity que muestra la cartelera del cine seleccionado.  */
///////////////////////////////////////////////////////////////
public class Cine extends ActionBar
{	
	ListView listCine;
	Cursor cursor;
	SimpleCursorAdapter adapter;
	String horario = "";	
	
	private static BaseDeDatos BD;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
    	horario = intent.getStringExtra("horario");
        
        if(horario.equals("horarioArtesietePeli"))
        {
        	setContentView(R.layout.activity_artesiete); 
        	listCine = (ListView) findViewById(R.id.listArtesiete);
        }
        else
        {
        	setContentView(R.layout.activity_luzcastilla); 
        	listCine = (ListView) findViewById(R.id.listLuzCastilla);
        }
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);		//permite volver a la pantalla principal

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
		
		Intent intent = getIntent();
    	horario = intent.getStringExtra("horario");
    	 	
    	final String[] FROM = {BaseDeDatos.C_NOMBRE, BaseDeDatos.C_DURACION, BaseDeDatos.C_PAIS, BaseDeDatos.C_EDAD, horario};
    	final int[] TO = {R.id.nombrePeli, R.id.duracionPeli, R.id.paisPeli, R.id.edadPeli, R.id.horarioPeli};

		//Muestra los datos especificados en FROM, en formato_lista_X.xml
		if(horario.equals("horarioArtesietePeli"))
        {
			cursor = BD.leerDatosArtesiete();		//lee los datos de la BD
			startManagingCursor(cursor);
			adapter = new SimpleCursorAdapter(this, R.layout.formato_lista_artesiete, cursor, FROM, TO);
        }
		else
		{
			cursor = BD.leerDatosLuzCastilla();		//lee los datos de la BD
			startManagingCursor(cursor);
			adapter = new SimpleCursorAdapter(this, R.layout.formato_lista_luzcastilla, cursor, FROM, TO);
		}
		listCine.setAdapter(adapter);
		listCine.setOnItemClickListener(new OnItemClickListener()
		{
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			  {
				  Intent intent = new Intent(Cine.this, PeliculaActivity.class);
				  intent.putExtra("id_peli", ""+id); //ID de la pelicula seleccionada
				  
				  startActivity(intent);
			  }
		});
	}
}
