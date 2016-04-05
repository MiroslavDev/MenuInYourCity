package com.miroslav.menuinyourcity.fragment;

import android.support.v4.app.Fragment;

import com.octo.android.robospice.Jackson2GoogleHttpClientSpiceService;
import com.octo.android.robospice.SpiceManager;

public abstract class BaseFragment extends Fragment {

    private SpiceManager spiceManager = new SpiceManager(Jackson2GoogleHttpClientSpiceService.class);

    @Override
    public void onStart() {
        super.onStart();
        spiceManager.start(getActivity());

    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }
}