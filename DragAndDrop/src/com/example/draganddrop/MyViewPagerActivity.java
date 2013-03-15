package com.example.draganddrop;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.TitlePageIndicator;

public class MyViewPagerActivity extends FragmentActivity {
	MyFragmentAdapter mAdapter;
	TitlePageIndicator indicator;
	ViewPager mPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager);
		mPager = (ViewPager)findViewById(R.id.pager);
		indicator = (TitlePageIndicator)findViewById(R.id.indicator);
		mAdapter = new MyFragmentAdapter(this, mPager, getSupportFragmentManager());
		indicator.setTextColor(Color.BLACK);
		indicator.setSelectedColor(getResources().getColor(R.color.ics_blue));
		indicator.setViewPager(mPager);
	}
	
	
}