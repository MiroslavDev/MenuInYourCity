package com.miroslav.menuinyourcity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.R;

/**
 * Created by apple on 4/20/16.
 */
public class MapFragment extends BaseFragment {
    private static final String TAG = "MapFragment";

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).setVisibleSpinnerInActBar();
        ((MainActivity) getActivity()).setTitleActBar("Map");


    }

    public static MapFragment newInstance() {
        MapFragment fr = new MapFragment();
        return fr;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_map, container, false);
    }




}
