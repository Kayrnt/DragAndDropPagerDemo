package com.example.draganddrop;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String TAG = "DBHelper";
	static final int DB_VERSION = 1;
	private static final String DB_NAME = "myDB.db";

	private static SQLiteDatabase db = null;

	public static final String _ID = "_id";
	public static final String CONTENT = "content";
	public static final String TYPE = "type";

	public static final String DATABASE_TABLE_ITEMS = "items";

	private static final String CREATE_TABLE_ITEMS = "create table " 
			+ DATABASE_TABLE_ITEMS + " (" +
			_ID + " integer primary key autoincrement, "
			+ TYPE + " text DEFAULT \"default\", " 
			+ CONTENT + " text not null);";


	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	public static SQLiteDatabase getInstance(Context context) {
		if (db == null) {
			DBHelper dbHelper = new DBHelper(context);
			db = dbHelper.getWritableDatabase();
		}
		return db;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_ITEMS);
		
		for(int i = 0; i < 10 ; i++) {
			ContentValues values = new ContentValues();
			values.put(CONTENT, "item "+i);
			values.put(TYPE, "type "+(i%2));
			db.insert(DATABASE_TABLE_ITEMS, null, values);
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
