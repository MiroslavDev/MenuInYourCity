package com.miroslav.menuinyourcity.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.R;

/**
 * Created by apple on 4/5/16.
 */
public class SplashFragment extends BaseFragment {
    private static final String TAG = "SplashFragment";
    private final int SPLASH_DISPLAY_LENGTH = 7000;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).hideActBar();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((MainActivity) getActivity()).addFragment(new CategoriesFragment());
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_splash, container, false);
    }
}
