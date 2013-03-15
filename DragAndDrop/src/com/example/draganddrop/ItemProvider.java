package com.example.draganddrop;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class ItemProvider extends ContentProvider {

	public static final String PROVIDER_NAME = "com.example.draganddrop.items";
	public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/items");
	private static final int ITEMS = 1;
	private static final int ITEMS_ID = 2;
	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "items", ITEMS);
		uriMatcher.addURI(PROVIDER_NAME, "items/#", ITEMS_ID);
	}

	private static final String DATABASE_TABLE = DBHelper.DATABASE_TABLE_ITEMS;

	SQLiteDatabase db;

	@Override
	public boolean onCreate() {
		Context context = getContext();
		db = DBHelper.getInstance(context);
		return (db == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
		sqlBuilder.setTables(DATABASE_TABLE);
		if (uriMatcher.match(uri) == ITEMS_ID) {
			sqlBuilder.appendWhere(DBHelper._ID + " = " + uri.getPathSegments().get(1));
		}
		if ((sortOrder == null) || (sortOrder == "")) {
			sortOrder = DBHelper._ID;
		}
		Cursor c = sqlBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		// ---register to watch a content URI for changes---
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case ITEMS:
			return "vnd.android.cursor.dir/vnd.dragandrop.items";
		case ITEMS_ID:
			return "vnd.android.cursor.item/vnd.dragandrop.items";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// ---add a note---
		long rowID = db.insert(DATABASE_TABLE, null, values);
		// ---if added successfully---
		if (rowID > 0) {
			Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(_uri, null);
			return _uri;
		}
		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// arg0 = uri
		// arg1 = selection
		// arg2 = selectionArgs
		int count = 0;
		switch (uriMatcher.match(arg0)) {
		case ITEMS:
			count = db.delete(DATABASE_TABLE, arg1, arg2);
			break;
		case ITEMS_ID:
			String id = arg0.getPathSegments().get(1);
			count = db.delete(DATABASE_TABLE, DBHelper._ID + " = " + id + (!TextUtils.isEmpty(arg1) ? " AND (" + arg1 + ')' : ""), arg2);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + arg0);
		}
		getContext().getContentResolver().notifyChange(arg0, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
		int count;
		switch (uriMatcher.match(uri)) {
		case ITEMS:
			count = db.update(DATABASE_TABLE, values, where, whereArgs);
			break;
		case ITEMS_ID:
			String id = uri.getPathSegments().get(1);
			count = db.update(DATABASE_TABLE, values, DBHelper._ID + " = " + id + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
