package com.miroslav.menuinyourcity.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.adapter.ShopsAdapter;
import com.miroslav.menuinyourcity.request.GetShops.ShopsModel;

import java.util.ArrayList;

/**
 * Created by apple on 4/22/16.
 */
public class LikedListFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    private ListView listView;

    public static LikedListFragment newInstance() {
        LikedListFragment fr = new LikedListFragment();
        return fr;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ListView) view.findViewById(R.id.frg_catalog_listview);
        listView.setAdapter(new ShopsAdapter(getContext(), new ArrayList<ShopsModel>(), null, null));
        listView.setOnItemClickListener(this);

        setupAB();
        loadFromDB();

    }

    private void setupAB() {
        ((MainActivity) getActivity()).setVisibleButtonBackInActBar();
        ((MainActivity) getActivity()).setTitleActBar(getString(R.string.liked));
    }

    private void loadFromDB() {
        SQLiteDatabase db = MainActivity.rootAcvitityInstance.getDbHelper().getWritableDatabase();
        Cursor c = db.query("likedList", null, null, null, null, null, null);
        ShopsAdapter adapter = (ShopsAdapter) listView.getAdapter();

        if (c.moveToFirst()) {

            int idColIndex = c.getColumnIndex("shop_id");
            int categoryIdColIndex = c.getColumnIndex("category_id");
            int cityIdColIndex = c.getColumnIndex("city_id");
            int titleColIndex = c.getColumnIndex("title");
            int descriptionColIndex = c.getColumnIndex("description");
            int timeColIndex = c.getColumnIndex("time");
            int latColIndex = c.getColumnIndex("lat");
            int lonColIndex = c.getColumnIndex("lon");
            int streetColIndex = c.getColumnIndex("street");
            int phoneColIndex = c.getColumnIndex("phone");
            int dateStartColIndex = c.getColumnIndex("date_start");
            int dateStopColIndex = c.getColumnIndex("date_stop");
            int updatedAtColIndex = c.getColumnIndex("updated_at");
            int ratingColIndex = c.getColumnIndex("rating");

            do {
                ShopsModel shopModel = new ShopsModel();

                shopModel.setId(c.getLong(idColIndex));
                shopModel.setCategoryId(c.getString(categoryIdColIndex));
                shopModel.setCityId(c.getString(cityIdColIndex));
                shopModel.setTitle(c.getString(titleColIndex));
                shopModel.setDescription(c.getString(descriptionColIndex));
                shopModel.setTime(c.getString(timeColIndex));
                shopModel.setLatitude(c.getString(latColIndex));
                shopModel.setLongitude(c.getString(lonColIndex));
                shopModel.setStreet(c.getString(streetColIndex));
                shopModel.setPhone(c.getString(phoneColIndex));
                shopModel.setDataStart(c.getString(dateStartColIndex));
                shopModel.setDataStop(c.getString(dateStopColIndex));
                shopModel.setUpdatedData(c.getString(updatedAtColIndex));
                shopModel.setRating(c.getDouble(ratingColIndex));

                adapter.add(shopModel);

            } while (c.moveToNext());
        }

        c.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ShopsAdapter adapter = (ShopsAdapter) listView.getAdapter();
        String title = adapter.getItem(position).getTitle();
        BaseFragment fr = DetailsShopFragment.newInstance(adapter.getItem(position).getId(), title);
        ((MainActivity) getActivity()).replaceFragment(fr);
    }
}
