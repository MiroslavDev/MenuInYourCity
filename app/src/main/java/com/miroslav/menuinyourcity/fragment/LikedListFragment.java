package com.miroslav.menuinyourcity.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.Model;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.adapter.ShopsAdapter;
import com.miroslav.menuinyourcity.dialogs.DeletedFromLikedListDialogFragment;
import com.miroslav.menuinyourcity.request.GetShops.ShopsModel;
import com.miroslav.menuinyourcity.request.GetShops.ShopsPhotosModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by apple on 4/22/16.
 */
public class LikedListFragment extends BaseFragment implements
        AdapterView.OnItemClickListener,
        ShopsAdapter.OnLikedImageClickListener,
        DeletedFromLikedListDialogFragment.DialogCallback {

    private ListView listView;
    private Map<Long, String> categoryNameSets= new HashMap<>();

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
        listView.setAdapter(new ShopsAdapter(getContext(), new ArrayList<ShopsModel>(), this, null));
        listView.setOnItemClickListener(this);

        setupAB();
        updateDataFromDB();

    }

    private void setupAB() {
        ((MainActivity) getActivity()).setVisibleButtonBackInActBar();
        ((MainActivity) getActivity()).setTitleActBar(getString(R.string.liked));
    }

    private void updateDataFromDB() {
        SQLiteDatabase db = MainActivity.rootAcvitityInstance.getDbHelper().getWritableDatabase();
        Cursor c = db.query("likedList", null, "city_id = ?", new String[] { Model.getInstance().currentCityId.toString() }, null, null, null, null);
        ShopsAdapter adapter = (ShopsAdapter) listView.getAdapter();
        adapter.clear();

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
            int imageURLColIndex = c.getColumnIndex("imageURL");
            int categoryNameIndex = c.getColumnIndex("category_name");

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
                shopModel.setPhotos(new ArrayList<ShopsPhotosModel>());

                ShopsPhotosModel shopsPhotosModel = new ShopsPhotosModel();
                shopsPhotosModel.setImage(c.getString(imageURLColIndex));
                shopModel.getPhotos().add(shopsPhotosModel);

                adapter.add(shopModel);

                categoryNameSets.put(c.getLong(idColIndex), c.getString(categoryNameIndex));

            } while (c.moveToNext());
        }

        c.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLikedImageClick(int position) {
        ShopsModel item = ((ShopsAdapter) listView.getAdapter()).getItem(position);
        DeletedFromLikedListDialogFragment dialog = new DeletedFromLikedListDialogFragment();
        dialog.setShopId(item.getId());
        dialog.setListener(this);
        dialog.show(getChildFragmentManager(), "");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ShopsAdapter adapter = (ShopsAdapter) listView.getAdapter();
        String title = categoryNameSets.get(adapter.getItem(position).getId());
        BaseFragment fr = DetailsShopFragment.newInstance(adapter.getItem(position).getId(), title);
        ((MainActivity) getActivity()).replaceFragment(fr);
    }

    @Override
    public void onResultFromDLG(Long id) {
        SQLiteDatabase db = MainActivity.rootAcvitityInstance.getDbHelper().getWritableDatabase();
        db.execSQL("DELETE FROM likedList WHERE shop_id=" + id + ";");
        db.close();
        updateDataFromDB();
    }
}
