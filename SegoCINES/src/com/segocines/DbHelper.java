package com.segocines;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	
	static final String TAG = "DbHelper";
	static final int VERSION = 1;
	static final String DATABASE = "segocines.db";
	static final String TABLE = "peliculas";
	static final String C_ID = "_id";
	static final String C_NOMBRE = "nombrePeli";
	static final String C_DIRECTOR = "directorPeli";
	static final String C_PRECIO = "precioPeli";
	
	// Constructor
	
	public DbHelper(Context context) {
	
		super(context, DATABASE, null, VERSION);
	}
	
	// Llamadado para crear la tabla
	
	@Override
	public void onCreate(SQLiteDatabase db) {
	
		Log.i(TAG, "Creating database: " + DATABASE);
	
		String sql = "create table " + TABLE + " (" + C_ID + " int primary key, " + C_NOMBRE + " text, " + C_DIRECTOR + " text, " + C_PRECIO + " double)";
	
		db.execSQL(sql);
	}
	
	// Llamado siempre que tengamos una nueva version
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
		// Aqui van las sentencias del tipo ALTER TABLE, de momento lo hacemos mas sencillo:
	
		db.execSQL("drop table if exists " + TABLE); // borra la vieja base de datos
	
		Log.d(TAG, "onUpdated");
	
		onCreate(db); // crea una base de datos nueva
	}

}
