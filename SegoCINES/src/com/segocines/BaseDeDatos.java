package com.segocines;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseDeDatos
{
	private static final String C = BaseDeDatos.class.getSimpleName();
	static final int VERSION = 1;
	static final String DATABASE = "segocines.db";
	static final String TABLE = "peliculas";
	static final String C_ID = "_id";
    static final String C_IMGPREVIA = "imgPreviaPeli";
    static final String C_IMG = "imgPeli";
    static final String C_NOMBRE = "nombrePeli";
    static final String C_NOMBREORIG = "nombreOrigPeli";
    static final String C_SINOPSIS = "sinopsisPeli";
    static final String C_EDAD = "edadPeli";
    static final String C_HORARIOARTESIETE = "horarioArtesietePeli";
    static final String C_HORARIOLUZCASTILLA = "horarioLuzCastillaPeli";
    static final String C_DIRECTOR = "directorPeli";
    static final String C_ANYO = "anyoPeli";
    static final String C_DURACION = "duracionPeli";
    static final String C_PAIS = "paisPeli";
    static final String C_GENERO = "generoPeli";
    static final String C_TRAILER = "trailerPeli";
	
	//Subclase DbHelper
	class DbHelper extends SQLiteOpenHelper
	{
		//Constructor
		public DbHelper(Context context)
		{
			super(context, DATABASE, null, VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{			
			Log.i(C, "Creating database: "+ DATABASE);
			
			db.execSQL("drop table if exists " + TABLE);
			
			db.execSQL("create table "+TABLE+" ("+C_ID+" int primary key, "+
				C_IMGPREVIA+" text, "+C_IMG+" text, "+
				C_NOMBRE+" text, "+C_NOMBREORIG+" text, "
				+C_SINOPSIS+" text, "+C_EDAD+" int, "+C_HORARIOARTESIETE+" text, "+C_HORARIOLUZCASTILLA+" text,"+
				C_DIRECTOR+" text, "+C_ANYO+" int, "+C_DURACION+" int, "+C_PAIS+" text, "+C_GENERO+" text, "+C_TRAILER+" text)");
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			db.execSQL("drop table if exists " + TABLE);
		
			this.onCreate(db);
		}
	}//Fin de la subclase DbHelper
	
	//M�todos
	
	public final DbHelper dbHelper;
	
	public BaseDeDatos(Context context)
	{
		this.dbHelper = new DbHelper(context);
	
		Log.i(C, "Initialized data");
	}
	
	public void close()
	{
		this.dbHelper.close();
	}
		
	public void insertOrIgnore(ContentValues values)
	{
		Log.d(C, "insertOrIgnore on " + values);
		
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		
		//ACTUALIZAR LA BASE DE DATOS
		/*db.execSQL("drop table if exists " + TABLE);
		
		db.execSQL("create table "+TABLE+" ("+C_ID+" int primary key, "+
				C_IMGPREVIA+" text, "+C_IMG+" text, "+
				C_NOMBRE+" text, "   +C_NOMBREORIG+" text, "+
				C_SINOPSIS+" text, " +C_EDAD+" int, "+C_HORARIOARTESIETE+" text, "+C_HORARIOLUZCASTILLA+" text,"+
				C_DIRECTOR+" text, " +C_ANYO+" int, "+C_DURACION+" int, "+C_PAIS+" text, "+C_GENERO+" text, "+C_TRAILER+" text)");
		*/
		try
		{
			db.insertWithOnConflict(TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
		} 
		finally
		{
			db.close();
		}
	}
	
	public Cursor leerDatos()
	{
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		
		return db.query(TABLE, null, null, null, null, null, null);
	}
}
