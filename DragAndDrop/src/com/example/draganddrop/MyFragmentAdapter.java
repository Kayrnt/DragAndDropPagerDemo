package com.example.draganddrop;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class MyFragmentAdapter extends FragmentPagerAdapter {
	public ArrayList<String> titles;
	private final ViewPager mViewPager;
	public MyFragmentAdapter(Activity activity, ViewPager pager, FragmentManager fm) {
		super(fm);
		titles = DBRequests.getTypes(activity);
		mViewPager = pager;
		mViewPager.setAdapter(this);
	}

	@Override
	public int getCount() {
		return titles.size();
	}

	@Override
	public Fragment getItem(int position) {
		Fragment f = null;
		if(titles.size()> 0) {
			f = new DragFragment();
			Bundle bundle = new Bundle();
			bundle.putString(DBHelper.TYPE, titles.get(position));
			f.setArguments(bundle);
		} else {
			f = new DragFragment();
		}
		return f;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles.get(position % titles.size()).toUpperCase();
	}

}