package com.example.draganddrop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;

public class DragFragment extends Fragment {
	DragDropNoteAdapter adapter;
	private DragSortListView mDslv;
	private DragSortController mController;
	CursorLoader loader;
	public int dragStartMode = DragSortController.ON_DOWN;
	public boolean sortEnabled = true;
	public boolean dragEnabled = true;
	public String title;

	public DragFragment() {}
	/** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(container == null) {
			return null;
		}
		getTitle();
		return mDslv = (DragSortListView) getActivity().getLayoutInflater().inflate(R.layout.dslv_fragment_main, null);
	}
	@Override
	public void onResume() {
		super.onResume();
		setupUI();
	}
	private void setupUI() {
		mController = new DragSortController(mDslv);
		mController.setDragHandleId(R.id.drag_handle);
		mController.setSortEnabled(sortEnabled);
		mController.setDragInitMode(dragStartMode);
		mDslv.setFloatViewManager(mController);
		mDslv.setOnTouchListener(mController);
		mDslv.setDragEnabled(dragEnabled);
		if(title != null) {
			loader = new CursorLoader(getActivity(), ItemProvider.CONTENT_URI, null, DBHelper.TYPE + " =  ?" , new String[] {title},  null);
		}
		else {
			loader = new CursorLoader(getActivity(), ItemProvider.CONTENT_URI, null, null , null,  null);
			title = "All my notes";
		}
		Log.i("DragFragment", "with title : " +title);
		adapter = new DragDropNoteAdapter(getActivity(), R.layout.list_item_handle_left, loader, 0);
		mDslv.setAdapter(adapter);
	}
	
	public void getTitle() {
		title = getArguments().getString(DBHelper.TYPE);
	}
}
