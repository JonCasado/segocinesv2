package com.segocines.adapter;

import java.io.File;

import com.segocines.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ImageCursorAdapter extends SimpleCursorAdapter
{
    private Cursor c;
    private Context context;
    private int layout;

    @SuppressWarnings("deprecation")
	public ImageCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to)
	{
	    super(context, layout, c, from, to);
	    this.c = c;
	    this.context = context;
	    this.layout = layout;
	}
	
	@SuppressLint("InflateParams")
	public View getView(int pos, View inView, ViewGroup parent)
	{
		View v = inView;
		if(v == null)
		{
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(layout, null);
		}
       
		this.c.moveToPosition(pos); 
		
		Log.i("POSIT", ""+layout);
       
		String nombre = this.c.getString(this.c.getColumnIndex("nombrePeli"));
		TextView txtV_Nombre = (TextView) v.findViewById(R.id.nombrePeli);
		txtV_Nombre.setText(nombre);
		
		String titleStr = this.c.getString(this.c.getColumnIndex("imgPeli"));
		File storage = Environment.getExternalStorageDirectory();
		File dir = new File(storage.getAbsoluteFile()+"/segocines");
		File imgFile = new File(dir, titleStr);
		
		//Lista peliculas
		if(layout == 2130903069)
		{
			ImageView myImage = (ImageView) v.findViewById(R.id.imgPreviaPeli);
			
			if(imgFile.exists())
			{
			   	Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			   	myImage.setImageBitmap(myBitmap);
			}
			else
	        {
	        	myImage.setImageResource(R.drawable.imgpeli_default);
	        }
			
			String horarioA = this.c.getString(this.c.getColumnIndex("horarioArtesietePeli"));
			TextView txtV_HorarioA = (TextView) v.findViewById(R.id.horarioAPeli);
			txtV_HorarioA.setText(horarioA);
			String horarioB = this.c.getString(this.c.getColumnIndex("horarioLuzCastillaPeli"));
			TextView txtV_HorarioB = (TextView) v.findViewById(R.id.horarioBPeli);
			txtV_HorarioB.setText(horarioB);
		}
		//Lista detallada pelicula
		else if(layout == 2130903072)
		{
			ImageView myImage = (ImageView) v.findViewById(R.id.imgPeli);
			
			if(imgFile.exists())
			{
				Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			   	myImage.setImageBitmap(myBitmap);
			}
			else
	        {
	        	myImage.setImageResource(R.drawable.imgpeli_default);
	        }
			
			String nombreOrig = this.c.getString(this.c.getColumnIndex("nombreOrigPeli"));
			TextView txtV_NombreOrig = (TextView) v.findViewById(R.id.nombreOrigPeli);
			txtV_NombreOrig.setText(nombreOrig);
			String anyo = this.c.getString(this.c.getColumnIndex("anyoPeli"));
			TextView txtV_Anyo = (TextView) v.findViewById(R.id.anyoPeli);
			txtV_Anyo.setText(anyo);
			String duracion = this.c.getString(this.c.getColumnIndex("duracionPeli"));
			TextView txtV_Duracion = (TextView) v.findViewById(R.id.duracionPeli);
			txtV_Duracion.setText(duracion);
			String pais = this.c.getString(this.c.getColumnIndex("paisPeli"));
			TextView txtV_Pais = (TextView) v.findViewById(R.id.paisPeli);
			txtV_Pais.setText(pais);
			String genero = this.c.getString(this.c.getColumnIndex("generoPeli"));
			TextView txtV_Genero = (TextView) v.findViewById(R.id.generoPeli);
			txtV_Genero.setText(genero);
			String edad = this.c.getString(this.c.getColumnIndex("edadPeli"));
			TextView txtV_Edad = (TextView) v.findViewById(R.id.edadPeli);
			txtV_Edad.setText(edad);
			String sinopsis = this.c.getString(this.c.getColumnIndex("sinopsisPeli"));
			TextView txtV_Sinopsis = (TextView) v.findViewById(R.id.sinopsisPeli);
			txtV_Sinopsis.setText(sinopsis);
			String horarioA = this.c.getString(this.c.getColumnIndex("horarioArtesietePeli"));
			TextView txtV_HorarioA = (TextView) v.findViewById(R.id.horarioAPeli);
			txtV_HorarioA.setText(horarioA);
			String horarioB = this.c.getString(this.c.getColumnIndex("horarioLuzCastillaPeli"));
			TextView txtV_HorarioB = (TextView) v.findViewById(R.id.horarioBPeli);
			txtV_HorarioB.setText(horarioB);
			
			final String trailer = this.c.getString(this.c.getColumnIndex("trailerPeli"));
			Button btnYT = (Button) v.findViewById(R.id.btnYT);
	  		btnYT.setOnClickListener(new OnClickListener()
	  		{
	  			@Override
	            public void onClick(View v) 
	            {
	  				if(trailer.contains("http://youtu.be/"))
	  				{
	  					Log.i("TRAIL good", trailer);
	  					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer));
	  					context.startActivity(browserIntent);
	  				}
	  				else
	  				{
	  					Log.i("TRAIL null", trailer);
	  					Toast.makeText(v.getContext(), v.getResources().getString(R.string.trailer_Error), Toast.LENGTH_SHORT).show();
	  				}
	            }
	        });
		}
		//Lista peliculas cine Artesiete
		else if(layout == 2130903070)
		{
			ImageView myImage = (ImageView) v.findViewById(R.id.imgPreviaPeli);
			
			if(imgFile.exists())
			{
			   	Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			   	myImage.setImageBitmap(myBitmap);
			}
			else
	        {
	        	myImage.setImageResource(R.drawable.imgpeli_default);
	        }
			
			String duracion = this.c.getString(this.c.getColumnIndex("duracionPeli"));
			TextView txtV_duracion = (TextView) v.findViewById(R.id.duracionPeli);
			txtV_duracion.setText(duracion);
			String pais = this.c.getString(this.c.getColumnIndex("paisPeli"));
			TextView txtV_pais = (TextView) v.findViewById(R.id.paisPeli);
			txtV_pais.setText(pais);
			String edad = this.c.getString(this.c.getColumnIndex("edadPeli"));
			TextView txtV_edad = (TextView) v.findViewById(R.id.edadPeli);
			txtV_edad.setText(edad);
			String horarioA = this.c.getString(this.c.getColumnIndex("horarioArtesietePeli"));
			TextView txtV_HorarioA = (TextView) v.findViewById(R.id.horarioPeli);
			txtV_HorarioA.setText(horarioA);
		}
		//Lista peliculas cine Luz Castilla
		else if(layout == 2130903071)
		{
			ImageView myImage = (ImageView) v.findViewById(R.id.imgPreviaPeli);
			
			if(imgFile.exists())
			{
			   	Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			   	myImage.setImageBitmap(myBitmap);
			}
			else
	        {
	        	myImage.setImageResource(R.drawable.imgpeli_default);
	        }
			
			String duracion = this.c.getString(this.c.getColumnIndex("duracionPeli"));
			TextView txtV_duracion = (TextView) v.findViewById(R.id.duracionPeli);
			txtV_duracion.setText(duracion);
			String pais = this.c.getString(this.c.getColumnIndex("paisPeli"));
			TextView txtV_pais = (TextView) v.findViewById(R.id.paisPeli);
			txtV_pais.setText(pais);
			String edad = this.c.getString(this.c.getColumnIndex("edadPeli"));
			TextView txtV_edad = (TextView) v.findViewById(R.id.edadPeli);
			txtV_edad.setText(edad);
			String horarioB = this.c.getString(this.c.getColumnIndex("horarioLuzCastillaPeli"));
			TextView txtV_HorarioB = (TextView) v.findViewById(R.id.horarioPeli);
			txtV_HorarioB.setText(horarioB);
		}
		
		return(v);
	}
}