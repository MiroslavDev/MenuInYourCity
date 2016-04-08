package com.miroslav.menuinyourcity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

/**
 * Created by apple on 4/8/16.
 */
public class HostSubcategoriesFragment extends Fragment {

    private Long parentId;

    public static HostSubcategoriesFragment newInstance(Long id) {
        HostSubcategoriesFragment fr = new HostSubcategoriesFragment();
        Bundle arg = new Bundle();
        arg.putLong(CatalogFragment.PARENT_ID, id);
        fr.setArguments(arg);
        return fr;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        parentId = getArguments().getLong(CatalogFragment.PARENT_ID);

        //setupActionBar();
        setupUI(view);
    }

    private void setupActionBar() {
        ((MainActivity) getActivity()).showActBar();
        ((MainActivity) getActivity()).setVisibleButtonBackInActBar();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_host_subcategories, container, false);
    }

    private void setupUI(View view) {

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getActivity().getSupportFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.category, CatalogFragment.class, new Bundler().putLong(CatalogFragment.PARENT_ID, parentId).get())
                .add(R.string.shares, SharesFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.view_pager_tab);
        viewPagerTab.setViewPager(viewPager);



    }
}
