package com.miroslav.menuinyourcity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.miroslav.menuinyourcity.ImageLoaderWithCache.ImageLoader;
import com.miroslav.menuinyourcity.adapter.SpinnerAdapter;
import com.miroslav.menuinyourcity.fragment.DetailPushFragment;
import com.miroslav.menuinyourcity.fragment.SplashFragment;
import com.miroslav.menuinyourcity.gcm.GCMManager;
import com.miroslav.menuinyourcity.request.Cities.BaseCitiesModel;
import com.miroslav.menuinyourcity.request.Cities.CitiesModel;
import com.miroslav.menuinyourcity.request.Cities.GetCitiesRequest;
import com.miroslav.menuinyourcity.request.StoreUsers.BaseStoreUsersModel;
import com.miroslav.menuinyourcity.request.StoreUsers.PostStoreUsersRequest;
import com.octo.android.robospice.Jackson2GoogleHttpClientSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "MainActivity";


    //381294291923

    private SpiceManager spiceManager = new SpiceManager(Jackson2GoogleHttpClientSpiceService.class);
    public static ImageLoader imageLoader;
    private View actBar, btnBackActBar, btnMenuActBar;
    private TextView titleActBar;
    private Spinner menuSpinner;
    private SpinnerAdapter adapter;
    private GetCitiesRequest getCitiesRequest;
    public static MainActivity rootAcvitityInstance = null;
    private DBHelper dbHelper;
    private Callbacks callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootAcvitityInstance = this;
        this.registerApp(GCMManager.getInstance().registrationId);
        imageLoader = new ImageLoader(this);

        if (this.checkPlayServices()) {
            GCMManager.getInstance().initialize(this);
        }
        setupUI();
        setupListener();
        dbHelper = new DBHelper(this);
        if(getIntent().getBooleanExtra("isPush", false)){
            DetailPushFragment fr =
                    DetailPushFragment.newInstance(getIntent().getStringExtra("message"),
                            getIntent().getStringExtra("image"), getIntent().getStringExtra("message"),
                            getIntent().getStringExtra("desc"), getIntent().getStringExtra("shop_id"));
            addFragment(fr);
        }else {
            addFragment(new SplashFragment());
        }

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


    @Override
    protected void onResume(){
        super.onResume();
        this.registerApp(GCMManager.getInstance().registrationId);
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
        btnBackActBar.setVisibility(View.INVISIBLE);
    }

    public void setVisibleButtonBackInActBar() {
        btnBackActBar.setVisibility(View.VISIBLE);
        btnMenuActBar.setVisibility(View.INVISIBLE);
    }

    public int getActBarHeight() {
        return actBar.getHeight();
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

    public DBHelper getDbHelper() {
        return dbHelper;
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
        Model.getInstance().currentCity = adapter.getItem(position).getName();
        Model.getInstance().currentCityId = adapter.getItem(position).getId();
        adapter.setCheckedPosition(position);
        adapter.notifyDataSetChanged();

        if(callback != null)
            callback.changeCity();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public SharedPreferences sharedPreferences() {
        return this.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
    }

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("Error", "This device is not supported.");
                Toast.makeText(this, "Play Services not supported.", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }


    public void registerApp(String token) {
        if (token == null) return;
        Log.d("push token: ", token);
        //todo send push token and type	(android/ios)
        //get storeUsers  POST http://menu.frameapp.com.ua/api/users/
        String deviceid = android.provider.Settings.Secure.getString(this.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        String imei = MD5(deviceid);
        PostStoreUsersRequest request = new PostStoreUsersRequest(token, imei);
        spiceManager.execute(request, request.getResourceUri(), request.getCacheExpiryDuration(), new RequestListener<BaseStoreUsersModel>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(BaseStoreUsersModel data) {
                if (data.getError() == false) {
                   Model.getInstance().currentUserId = data.getMessage();
                }
                //else {
                    //Toast.makeText(MainActivity.this, data.getMessage().toString(), Toast.LENGTH_LONG).show();
                //}
            }
        });
    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString().toUpperCase();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public interface Callbacks {
        void changeCity();
    }

    public void setCallback(Callbacks callback) {
        this.callback = callback;
    }


}
