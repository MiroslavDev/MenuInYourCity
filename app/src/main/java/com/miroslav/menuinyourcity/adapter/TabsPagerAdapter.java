package com.miroslav.menuinyourcity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.miroslav.menuinyourcity.fragment.CatalogFragment;
import com.miroslav.menuinyourcity.fragment.SharesFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	int mNumOfTabs;
	Long parentId;
	public TabsPagerAdapter(FragmentManager fm, int NumOfTabs, Long parentId) {
		super(fm);
		this.mNumOfTabs = NumOfTabs;
		this.parentId = parentId;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			Log.d("parentId = ", parentId+"");
			return new CatalogFragment().newInstance(parentId);
		case 1:
			// Games fragment activity
			return new SharesFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		return mNumOfTabs;

	}

	String[] titles = { "Каталог", "Акции"};

	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}



}
