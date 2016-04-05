package com.miroslav.menuinyourcity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.miroslav.menuinyourcity.fragment.SplashFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FrameLayout mainFragmentLayout;
    private View actBar, btnBackActBar, btnMenuActBar;
    private TextView titleActBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();

        addFragment(new SplashFragment());
    }

    public void setVisiblSlcCitiInActBar() {
        btnMenuActBar.setVisibility(View.VISIBLE);
        //btnBackActBar.setVisibility(View.GONE);
    }

    public void setVisiblButtonBackInActBar() {
        //btnBackActBar.setVisibility(View.VISIBLE);
        btnMenuActBar.setVisibility(View.GONE);
    }

    public void setTitleActBar(String title) {
        titleActBar.setText(title);
    }

    public void showActBar() {
        actBar.setVisibility(View.VISIBLE);
    }

    public void hideActBar() {
        actBar.setVisibility(View.GONE);
    }

    private void setupUI() {
        actBar = findViewById(R.id.toolbar_actionbar);
        mainFragmentLayout = (FrameLayout) findViewById(R.id.main_fragment_container);
        btnBackActBar = findViewById(R.id.main_fragment_container);
        btnMenuActBar = findViewById(R.id.menu_actbar_btn);
        titleActBar = (TextView) findViewById(R.id.title_actbar);
    }

    public void replaceFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).addToBackStack(null).commit();
    }

    public void addFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction().add(R.id.main_fragment_container, fragment).commit();
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment).addToBackStack(null).commit();
    }

    public void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, fragment).commit();
    }
}
