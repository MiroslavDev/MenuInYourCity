package com.miroslav.menuinyourcity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.crittercism.app.Crittercism;
import com.miroslav.menuinyourcity.MainActivity;
import com.octo.android.robospice.Jackson2GoogleHttpClientSpiceService;
import com.octo.android.robospice.SpiceManager;

public abstract class BaseFragment extends Fragment {

    protected SpiceManager spiceManager = new SpiceManager(Jackson2GoogleHttpClientSpiceService.class);

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            Crittercism.initialize(getActivity(),
                    "9df78e5d8b3243289652e05a3de44c6000555300");
        } catch (NoClassDefFoundError e) {

        }
        ((MainActivity) getActivity()).showActBar();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!spiceManager.isStarted()){
            spiceManager.start(getActivity());
        }
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        ((MainActivity) getActivity()).collapseSearchToolbar();
        super.onDestroyView();
    }
}