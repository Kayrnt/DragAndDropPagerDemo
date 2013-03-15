package com.example.draganddrop;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.view.View;
import android.widget.TextView;

import com.mobeta.android.dslv.ResourceDragSortCursorAdapter;

public class DragDropNoteAdapter extends ResourceDragSortCursorAdapter {
	int contentColumn;
	int idColumn;
	CursorLoader loader;
	Activity activity;

	public DragDropNoteAdapter(Activity activity, int layout, CursorLoader loader, int flags) {
		super(activity, layout, loader.loadInBackground(), flags);
		this.loader = loader;
		contentColumn = mCursor.getColumnIndex(DBHelper.CONTENT); //the column in my DB table that refers to the content of my list item
		this.activity = activity;
	}

	@Override
	public void bindView(View view, final Context context, Cursor cursor) {
		final TextView tContent = (TextView) view.findViewById(R.id.text);
		final String content = cursor.getString(contentColumn);
		tContent.setText(content);
	}

}