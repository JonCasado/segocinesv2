package com.segocines.activities;

import com.segocines.R;
import com.segocines.adapter.ImageCursorAdapter;
import com.segocines.bd.BaseDeDatos;
import com.segocines.model.ActionBar;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/*************************************************************/
/** @Author = ("Joaquin Casas", "Jon Casado")				**/
/*************************************************************/
///////////////////////////////////////////////////////////////
/* Activity que muestra la informacion de la pelicula		 */
/* seleccionada.											 */
///////////////////////////////////////////////////////////////
public class PeliculaActivity extends ActionBar
{	
	ListView listPeli;
	Cursor cursor;
	SimpleCursorAdapter adapter;
	Button btnYT;

	static final String[] FROM = {BaseDeDatos.C_NOMBRE, BaseDeDatos.C_NOMBREORIG, BaseDeDatos.C_ANYO, BaseDeDatos.C_DURACION, BaseDeDatos.C_PAIS, BaseDeDatos.C_GENERO, BaseDeDatos.C_EDAD, BaseDeDatos.C_SINOPSIS, BaseDeDatos.C_HORARIOARTESIETE, BaseDeDatos.C_HORARIOLUZCASTILLA};
	static final int[] TO = {R.id.nombrePeli, R.id.nombreOrigPeli, R.id.anyoPeli,  R.id.duracionPeli, R.id.paisPeli, R.id.generoPeli, R.id.edadPeli, R.id.sinopsisPeli, R.id.horarioAPeli, R.id.horarioBPeli};
	private static BaseDeDatos BD;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peli);  
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        listPeli = (ListView) findViewById(R.id.listPeli);

        BD = new BaseDeDatos(this);
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();

		BD.close();
	}
	
	@Override
	public void onBackPressed()
	{
		finish();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onResume()
	{
		super.onResume();
		
		String id_peli = getIntent().getStringExtra("id_peli");	//recoge el ID de la pelicula seleccionada
		
		cursor = BD.leerDatosPelicula(id_peli);
		startManagingCursor(cursor);

		adapter = new ImageCursorAdapter(this, R.layout.formato_lista_peli, cursor, FROM, TO);
		listPeli.setAdapter(adapter);

		/*btnYT = (Button) findViewById(R.id.btnYT);
  		btnYT.setOnClickListener(new OnClickListener()
  		{
  			@Override
            public void onClick(View v) 
            {
            	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=vuJt_lGYf70"));
              	startActivity(browserIntent);
            }
        });*/
	}
}
