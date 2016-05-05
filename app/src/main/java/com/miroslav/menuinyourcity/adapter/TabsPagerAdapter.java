package com.miroslav.menuinyourcity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.miroslav.menuinyourcity.fragment.CatalogFragment;
import com.miroslav.menuinyourcity.fragment.SharesFragment;

public class TabsPagerAdapter extends FragmentStatePagerAdapter {
	int mNumOfTabs;
	Long parentId;
	Boolean isFollow;
	public TabsPagerAdapter(FragmentManager fm, int NumOfTabs, Long parentId, Boolean isFollow) {
		super(fm);
		this.mNumOfTabs = NumOfTabs;
		this.parentId = parentId;
		this.isFollow = isFollow;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			Log.d("parentId = ", parentId+"");
			return CatalogFragment.newInstance(parentId);
		case 1:
			return SharesFragment.newInstance(parentId, isFollow);
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
