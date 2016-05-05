package com.miroslav.menuinyourcity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.Model;
import com.miroslav.menuinyourcity.R;

/**
 * Created by apple on 4/20/16.
 */
public class MapFragment extends BaseFragment implements OnMapReadyCallback {
    private static final String TAG = "MapFragment";

    private static final String LAT_KEY = "latitude key";
    private static final String LON_KEY = "longitude key";
    private static final String TITLE_ACTION_BAR_KEY = "action bar key";

    private Double latitude;
    private Double longitude;

    private int zoomLevel = 12;

    public static MapFragment newInstance(Double latitude, Double longitude, String actBarTitle) {
        MapFragment fr = new MapFragment();
        Bundle arg = new Bundle();
        arg.putDouble(LAT_KEY, latitude);
        arg.putDouble(LON_KEY, longitude);
        arg.putString(TITLE_ACTION_BAR_KEY, actBarTitle);
        fr.setArguments(arg);
        return fr;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = getArguments().getString(TITLE_ACTION_BAR_KEY);
        latitude = getArguments().getDouble(LAT_KEY);
        longitude = getArguments().getDouble(LON_KEY);

        ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg_support_map)).getMapAsync(this);

        setActionBarTitle(title);
    }

    private void setActionBarTitle(String title) {
        ((MainActivity)getActivity()).setVisibleButtonBackInActBar();
        ((MainActivity) getActivity()).setTitleActBar(Model.getInstance().currentCity + ", " + title);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_map, container, false);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng coordinate = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(coordinate));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, zoomLevel));
    }
}
