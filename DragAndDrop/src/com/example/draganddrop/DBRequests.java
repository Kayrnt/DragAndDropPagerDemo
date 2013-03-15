package com.example.draganddrop;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBRequests {

	public static ArrayList<String> getTypes(Context context){
		ArrayList<String> types = new ArrayList<String>();
		SQLiteDatabase db = DBHelper.getInstance(context);
		Cursor cursor = db.query(true, DBHelper.DATABASE_TABLE_ITEMS, new String[] {DBHelper.TYPE}, null, null, null, null, null, null);
		cursor.moveToFirst();
		int typeColumn = cursor.getColumnIndex(DBHelper.TYPE);
		for(int i = 0; i < cursor.getCount(); i++) {
			types.add(cursor.getString(typeColumn));
			cursor.moveToNext();
		}
		cursor.close();
		return types;
	}
	
	public static int getTypesCount(Context context){
		SQLiteDatabase db = DBHelper.getInstance(context);
		Cursor cursor = db.query(true, DBHelper.DATABASE_TABLE_ITEMS, new String[] {DBHelper.TYPE}, null, null, null, null, null, null);
		int size = cursor.getCount();
		cursor.close();
		return size;
	}


}
