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
/* Activity que muestra la cartelera de los cines Artesiete. */
///////////////////////////////////////////////////////////////
public class ArteSiete extends ActionBar
{	
	ListView listArtesiete;
	Cursor cursor;
	SimpleCursorAdapter adapter;

	static final String[] FROM = {BaseDeDatos.C_NOMBRE, BaseDeDatos.C_DURACION, BaseDeDatos.C_PAIS, BaseDeDatos.C_EDAD, BaseDeDatos.C_HORARIOARTESIETE};
	static final int[] TO = {R.id.nombrePeli, R.id.duracionPeli, R.id.paisPeli, R.id.edadPeli, R.id.horarioPeli};
	private static BaseDeDatos BD;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artesiete);  
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);		//permite volver a la pantalla principal
        
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

		cursor = BD.leerDatosArtesiete();		//lee los datos de la BD
		startManagingCursor(cursor);

		//Muestra los datos especificados en FROM, en formato_lista_artesiete.xml
		adapter = new SimpleCursorAdapter(this, R.layout.formato_lista_artesiete, cursor, FROM, TO);
		listArtesiete.setAdapter(adapter);
		listArtesiete.setOnItemClickListener(new OnItemClickListener()
		{
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			  {
				  Intent intent = new Intent(ArteSiete.this, PeliculaActivity.class);
				  intent.putExtra("id_peli", ""+id); //ID de la pelicula seleccionada
				  
				  startActivity(intent);
			  }
		});
	}
}
