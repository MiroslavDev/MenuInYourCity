package com.miroslav.menuinyourcity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crittercism.app.Crittercism;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.miroslav.menuinyourcity.adapter.SpinnerAdapter;
import com.miroslav.menuinyourcity.fragment.BaseFragment;
import com.miroslav.menuinyourcity.fragment.DetailPushFragment;
import com.miroslav.menuinyourcity.fragment.SearchFragment;
import com.miroslav.menuinyourcity.fragment.SplashFragment;
import com.miroslav.menuinyourcity.fragment.TaxiFragment;
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
    private View actBar, btnBackActBar, btnMenuActBar;
    private TextView titleActBar;
    private Spinner menuSpinner;
    private SpinnerAdapter adapter;
    public static MainActivity rootAcvitityInstance = null;
    private DBHelper dbHelper;
    private Callbacks callback;
    private Toolbar toolbar;
    private MenuItem searchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Crittercism.initialize(getApplicationContext(),
                    "9df78e5d8b3243289652e05a3de44c6000555300");
        } catch (NoClassDefFoundError e) {

        }

        setContentView(R.layout.activity_main);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        rootAcvitityInstance = this;
        this.registerApp(GCMManager.getInstance().registrationId);

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
            addFragmentWithoutBackStack(fr);
        }else {
            addFragmentWithoutBackStack(new SplashFragment());
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        spiceManager.start(this);
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
        citiesRequest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);


        final MenuItem taxiItem = menu.findItem(R.id.action_taxi);
        searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() >= 3)
                    ((SearchFragment) getFragmentByTag(SearchFragment.TAG)).searchRequest(newText);

                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                removeFragmentByTag(SearchFragment.TAG);
                return false;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                taxiItem.setVisible(true);
                removeFragmentByTag(SearchFragment.TAG);
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                taxiItem.setVisible(false);
                BaseFragment fr = SearchFragment.newInstance();
                addFragmentWithoutBackStackWithTag(fr, SearchFragment.TAG);
                return true;
            }
        });


        return true;
    }

    public void collapseSearchToolbar() {
        searchItem.collapseActionView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            //TODO SearchFragment.NewInstance set editText listener
            return true;
        } else if(item.getItemId() == R.id.action_taxi) {
            Fragment fragment = getFragmentByTag(TaxiFragment.TAG);
            if(fragment == null || !fragment.isVisible())
                replaceFragmentWithTag(TaxiFragment.newInstance(), TaxiFragment.TAG);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void citiesRequest() {
        GetCitiesRequest getCitiesRequest = new GetCitiesRequest();
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
        toolbar.setVisibility(View.VISIBLE);
    }

    public void hideActBar() {
        actBar.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
    }

    private void setupUI() {
        actBar = findViewById(R.id.toolbar_actionbar);
        btnBackActBar = findViewById(R.id.btn_back_actbar);
        btnMenuActBar = findViewById(R.id.menu_actbar_btn);
        titleActBar = (TextView) findViewById(R.id.title_actbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

                popBackStackSupportFragmentManager();
                if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Вы дейстивтельно хотите выйти?")
                            .setCancelable(false)
                            .setPositiveButton("Да",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    closeApplication();
                                }
                            })
                    .setNegativeButton("Нет", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });


                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }

    private void closeApplication() {
        rootAcvitityInstance = null;
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        btnBackActBar.callOnClick();
    }

    public void setOnButtonBackListener(View.OnClickListener listener) {
        if(listener != null)
            btnBackActBar.setOnClickListener(listener);
        else
            setupListener();
    }

    public void popBackStackSupportFragmentManager() {
        getSupportFragmentManager().popBackStack();
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

    public void addFragmentWithoutBackStackWithTag(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, fragment, tag).commit();
    }

    public void replaceFragmentWithTag(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment, tag).addToBackStack(null).commit();
    }

    public Fragment getFragmentByTag(String name) {
        return getSupportFragmentManager().findFragmentByTag(name);
    }

    public void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, fragment).addToBackStack(null).commit();
    }

    public void addFragmentWithoutBackStack(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, fragment).commit();
    }

    public void removeFragmentByTag(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if(fragment != null)
            getSupportFragmentManager().beginTransaction().hide(fragment).remove(fragment).commit();
    }

    public void addCities(List<CitiesModel> data) {
        adapter.clear();
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
        return getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
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
