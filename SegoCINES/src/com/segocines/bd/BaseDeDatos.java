package com.segocines.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/*************************************************************/
/** @Author = ("Joaquin Casas", "Jon Casado")				**/
/*************************************************************/
///////////////////////////////////////////////////////////////
/* Activity que muestra la cartelera de los cines Artesiete. */
///////////////////////////////////////////////////////////////
public class BaseDeDatos
{
	private static final String C = BaseDeDatos.class.getSimpleName();
	static final int VERSION = 1;
	static final String DATABASE = "segocines.db";
	static final String TABLE = "peliculas";
	public static final String C_ID = "_id";
	public static final String C_IMGPREVIA = "imgPreviaPeli";
	public static final String C_IMG = "imgPeli";
    public static final String C_NOMBRE = "nombrePeli";
    public static final String C_NOMBREORIG = "nombreOrigPeli";
    public static final String C_SINOPSIS = "sinopsisPeli";
    public static final String C_EDAD = "edadPeli";
    public static final String C_HORARIOARTESIETE = "horarioArtesietePeli";
    public static final String C_HORARIOLUZCASTILLA = "horarioLuzCastillaPeli";
    public static final String C_DIRECTOR = "directorPeli";
    public static final String C_ANYO = "anyoPeli";
    public static final String C_DURACION = "duracionPeli";
    public static final String C_PAIS = "paisPeli";
    public static final String C_GENERO = "generoPeli";
    public static final String C_TRAILER = "trailerPeli";
	
    
    ///////////////////////////////////////////////////////////////
    /* SubClase que permite crear y actualizar la BD. 			 */
    ///////////////////////////////////////////////////////////////
	class DbHelper extends SQLiteOpenHelper
	{
		public DbHelper(Context context)
		{
			super(context, DATABASE, null, VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{			
			Log.i(C, "Creating database: "+ DATABASE);
			
			db.execSQL("drop table if exists " + TABLE);	//elimina la BD
			
			//Crea la BD
			db.execSQL("create table "+TABLE+" ("+C_ID+" int primary key, "+
				C_IMGPREVIA+" text, "+C_IMG+" text, "+
				C_NOMBRE+" text, "+C_NOMBREORIG+" text, "
				+C_SINOPSIS+" text, "+C_EDAD+" text, "+C_HORARIOARTESIETE+" text, "+C_HORARIOLUZCASTILLA+" text,"+
				C_DIRECTOR+" text, "+C_ANYO+" int, "+C_DURACION+" int, "+C_PAIS+" text, "+C_GENERO+" text, "+C_TRAILER+" text)");
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			db.execSQL("drop table if exists " + TABLE);
		
			this.onCreate(db);
		}
	}//FIN-DbHelper
	
	
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
		
	
	///////////////////////////////////////////////////////////////
	/* Escribe en la BD los values. 							 */
	///////////////////////////////////////////////////////////////
	public void insertOrIgnore(ContentValues values)
	{
		Log.d(C, "insertOrIgnore on " + values);
		
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		
		//ACTUALIZAR LA BASE DE DATOS
		/*db.execSQL("drop table if exists " + TABLE);
		
		db.execSQL("create table "+TABLE+" ("+C_ID+" int primary key, "+
				C_IMGPREVIA+" text, "+C_IMG+" text, "+
				C_NOMBRE+" text, "   +C_NOMBREORIG+" text, "+
				C_SINOPSIS+" text, " +C_EDAD+" text, "+C_HORARIOARTESIETE+" text, "+C_HORARIOLUZCASTILLA+" text,"+
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
	
	
	///////////////////////////////////////////////////////////////
	/* Lee los datos de la BD. 									 */
	///////////////////////////////////////////////////////////////
	public Cursor leerDatos()
	{
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		
		return db.query(TABLE, null, null, null, null, null, null);
	}
	//FIN-leerDatos
	
	
	///////////////////////////////////////////////////////////////
	/* Lee los datos de la BD. 									 */
	///////////////////////////////////////////////////////////////
	public Cursor leerDatosArtesiete()
	{
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		
		return db.query(TABLE, null, BaseDeDatos.C_HORARIOARTESIETE+" != 'No disponible'", null, null, null, null);
	}
	//FIN-leerDatos
	
	///////////////////////////////////////////////////////////////
	/* Lee los datos de la BD. 									 */
	///////////////////////////////////////////////////////////////
	public Cursor leerDatosLuzCastilla()
	{
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		
		return db.query(TABLE, null, BaseDeDatos.C_HORARIOLUZCASTILLA+" != 'No disponible'", null, null, null, null);
	}
	//FIN-leerDatos
	
	
	///////////////////////////////////////////////////////////////
	/* Lee los datos de la BD. 									 */
	///////////////////////////////////////////////////////////////
	public Cursor leerDatosPelicula(String id_peli)
	{
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		
		return db.query(TABLE, null, C_ID+" == '"+id_peli+"'", null, null, null, null);
	}
	//FIN-leerDatos
}
