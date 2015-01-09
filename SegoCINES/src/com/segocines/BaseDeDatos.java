package com.segocines;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseDeDatos {

	private static final String TAG = BaseDeDatos.class.getSimpleName();
	static final int VERSION = 1;
	static final String DATABASE = "segocines.db";
	static final String TABLE = "peliculas";
	public static final String C_ID = "_id";
	public static final String C_NOMBRE = "nombrePeli";
	public static final String C_DIRECTOR = "directorPeli";
	public static final String C_PRECIO = "precioPeli";
	
	
	//Subclase DbHelper
	
	class DbHelper extends SQLiteOpenHelper {
	
		//Constructor
		public DbHelper(Context context) {
	
			super(context, DATABASE, null, VERSION);
	}
		
	@Override
	public void onCreate(SQLiteDatabase db) {
		
	Log.i(TAG, "Creating database: " + DATABASE);
	
	db.execSQL("create table " + TABLE + " (" + C_ID + " int primary key, " +
	
			C_NOMBRE + " text, " + C_DIRECTOR + " text, " + C_PRECIO + " double)");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("drop table if exists " + TABLE);
	
		this.onCreate(db);
	
	}
	
	}//Fin de la subclase DbHelper
	
	//Métodos
	
	private final DbHelper dbHelper;
	
	public BaseDeDatos(Context context) {
	
		this.dbHelper = new DbHelper(context);
	
		Log.i(TAG, "Initialized data");
	}
	
	public void close() {
		
		this.dbHelper.close();
		
	}
		
	public void insertOrIgnore(ContentValues values) {
		
		Log.d(TAG, "insertOrIgnore on " + values);
		
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		
		try {
		
			db.insertWithOnConflict(TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
		} 
		finally {
		
			db.close(); //
		}
	}
}
