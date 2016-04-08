package com.miroslav.menuinyourcity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.miroslav.menuinyourcity.adapter.SpinnerAdapter;
import com.miroslav.menuinyourcity.fragment.SplashFragment;
import com.miroslav.menuinyourcity.request.Cities.BaseCitiesModel;
import com.miroslav.menuinyourcity.request.Cities.CitiesModel;
import com.miroslav.menuinyourcity.request.Cities.GetCitiesRequest;
import com.octo.android.robospice.Jackson2GoogleHttpClientSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "MainActivity";

    private SpiceManager spiceManager = new SpiceManager(Jackson2GoogleHttpClientSpiceService.class);

    private View actBar, btnBackActBar, btnMenuActBar;
    private TextView titleActBar;
    private Spinner menuSpinner;
    private SpinnerAdapter adapter;
    private GetCitiesRequest getCitiesRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
        setupListener();
        addFragment(new SplashFragment());
    }

    @Override
    public void onStart() {
        super.onStart();
        spiceManager.start(this);
        if(getCitiesRequest == null)
            citiesRequest();
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    private void citiesRequest() {
        getCitiesRequest = new GetCitiesRequest();
        spiceManager.execute(getCitiesRequest, getCitiesRequest.getResourceUri(), getCitiesRequest.getCacheExpiryDuration(), new RequestListener<BaseCitiesModel>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(BaseCitiesModel baseCitiesModel) {
                if (!baseCitiesModel.getError()) {
                    addCities(baseCitiesModel.getCitiesModel());
                } else {
                    Toast.makeText(MainActivity.this, baseCitiesModel.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void setVisibleSpinnerInActBar() {
        btnMenuActBar.setVisibility(View.VISIBLE);
        btnBackActBar.setVisibility(View.GONE);
    }

    public void setVisibleButtonBackInActBar() {
        btnBackActBar.setVisibility(View.VISIBLE);
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
        btnBackActBar = findViewById(R.id.btn_back_actbar);
        btnMenuActBar = findViewById(R.id.menu_actbar_btn);
        titleActBar = (TextView) findViewById(R.id.title_actbar);

        adapter = new SpinnerAdapter(this, new ArrayList<CitiesModel>());
        adapter.setDropDownViewResource(R.layout.spinner_item);

        menuSpinner = (Spinner) findViewById(R.id.menu_actbar_btn_icon);
        menuSpinner.setAdapter(adapter);

        menuSpinner.setOnItemSelectedListener(this);
    }

    private void setupListener() {
        btnBackActBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStack();
            }
        });
    }

    public void setOnButtonBackListener(View.OnClickListener listener) {
        btnBackActBar.setOnClickListener(listener);
    }

    public void replaceFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).addToBackStack(null).commit();
    }

    public void addFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction().add(R.id.main_fragment_container, fragment).commit();
    }

    public void replaceFragmentWithoutBackStack(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment).addToBackStack(null).commit();
    }

    public void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, fragment).commit();
    }

    public void addCities(List<CitiesModel> data) {
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        setTitleActBar(adapter.getItem(position).getName());
        adapter.setCheckedPosition(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
