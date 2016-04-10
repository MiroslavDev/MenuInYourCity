package com.miroslav.menuinyourcity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.Model;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.adapter.TabsPagerAdapter;

/**
 * Created by apple on 4/8/16.
 */
public class HostSubcategoriesFragment extends Fragment {

    private static final String NAME_SUBCATEGORY = "name_subcategory";

    private Long parentId;
    private String nameSubcategory;

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private TabLayout tabLayout;


    public static HostSubcategoriesFragment newInstance(Long id, String nameSubcategory) {
        HostSubcategoriesFragment fr = new HostSubcategoriesFragment();
        Bundle arg = new Bundle();
        arg.putLong(CatalogFragment.PARENT_ID, id);
        arg.putString(NAME_SUBCATEGORY, nameSubcategory);
        fr.setArguments(arg);
        return fr;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        parentId = getArguments().getLong(CatalogFragment.PARENT_ID);
        nameSubcategory = getArguments().getString(NAME_SUBCATEGORY);

        setupActionBar();
        setupUI(view);
    }

    private void setupActionBar() {
        ((MainActivity) getActivity()).setVisibleButtonBackInActBar();
        ((MainActivity) getActivity()).setTitleActBar(Model.getInstance().currentCity +  ", " + nameSubcategory);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_host_subcategories, container, false);
    }

    private void setupUI(View view) {

        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Каталог"));
        tabLayout.addTab(tabLayout.newTab().setText("Акции"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        Log.d("parentId = ", parentId+"");
        mAdapter = new TabsPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount(), parentId);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Log.d("onPageSelected ", position + "");
                tabLayout.getTabAt(position).select();

                if(position == 0) {
                    ((MainActivity) getActivity()).setTitleActBar(Model.getInstance().currentCity +  ", " + nameSubcategory);
                } else {
                    ((MainActivity) getActivity()).setTitleActBar(getString(R.string.shares) +  ", " + nameSubcategory);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("onTabSelected", tab.getPosition() + "");
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
