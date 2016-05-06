package com.miroslav.menuinyourcity.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.R;

/**
 * Created by apple on 4/5/16.
 */
public class SplashFragment extends Fragment {
    private static final String TAG = "SplashFragment";
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private TextView numberTV;
    private Handler handler = new Handler();
    private Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            ((MainActivity) getActivity()).replaceFragmentWithoutBackStack(new CategoriesFragment());
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).hideActBar();

        numberTV = (TextView) view.findViewById(R.id.frg_splash_phone_number);
        view.findViewById(R.id.frg_splash_call_ic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + numberTV.getText()));
                startActivity(intent);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_splash, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

            handler.postDelayed(myRunnable, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onStop() {
        handler.removeCallbacks(myRunnable);
        super.onStop();
    }
}
