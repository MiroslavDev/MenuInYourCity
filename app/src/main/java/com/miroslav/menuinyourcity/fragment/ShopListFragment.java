package com.miroslav.menuinyourcity.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.miroslav.menuinyourcity.DBHelper;
import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.adapter.ShopsAdapter;
import com.miroslav.menuinyourcity.request.GetShops.BaseGetShopsModel;
import com.miroslav.menuinyourcity.request.GetShops.ShopsModel;
import com.miroslav.menuinyourcity.request.GetShops.GetShopsByCategoryRequest;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by apple on 4/10/16.
 */
public class ShopListFragment extends BaseFragment implements AdapterView.OnItemClickListener, ShopsAdapter.OnLikedImageClickListener {

    public  static final String TITLE = "title";
    public  static final String SHOP_ID = "parent_id";

    private List<ShopsModel> data;
    private ListView listView;
    private String title;
    private Set<Long> likedList = new HashSet<>();

    public static ShopListFragment newInstance(Long id, String title) {
        ShopListFragment fr = new ShopListFragment();
        Bundle arg = new Bundle();
        arg.putLong(SHOP_ID, id);
        arg.putString(TITLE, title);
        fr.setArguments(arg);
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

        Long id = getArguments().getLong(SHOP_ID);
        title = getArguments().getString(TITLE);

        fillLikedList();


        listView = (ListView) view.findViewById(R.id.frg_catalog_listview);
        listView.setAdapter(new ShopsAdapter(getContext(), new ArrayList<ShopsModel>(), this, likedList));
        listView.setOnItemClickListener(this);

        setupAB();
        shopsRequest(id);

    }

    private void fillLikedList() {
        SQLiteDatabase db = MainActivity.rootAcvitityInstance.getDbHelper().getWritableDatabase();
        Cursor c = db.query("likedList", null, null, null, null, null, null);

        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("shop_id");

            do {
                likedList.add(c.getLong(idColIndex));
            } while (c.moveToNext());
        }

        c.close();
    }

    private void setupAB() {
        ((MainActivity) getActivity()).setVisibleButtonBackInActBar();
        ((MainActivity) getActivity()).setTitleActBar(title);
    }

    private void shopsRequest(Long id) {
        GetShopsByCategoryRequest request = new GetShopsByCategoryRequest(id);
        spiceManager.execute(request, request.getResourceUri(), request.getCacheExpiryDuration(), new RequestListener<BaseGetShopsModel>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(BaseGetShopsModel data) {
                if (!data.getError()) {
                    updaateAdapterData(data.getShopsModel());
                } else {
                    Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updaateAdapterData(List<ShopsModel> data) {
        this.data = data;
        ShopsAdapter adapter = (ShopsAdapter) listView.getAdapter();
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ShopsAdapter adapter = (ShopsAdapter) listView.getAdapter();
        BaseFragment fr = DetailsShopFragment.newInstance(adapter.getItem(position).getId(), title);
        ((MainActivity) getActivity()).replaceFragment(fr);
    }

    @Override
    public void onLikedImageClick(int position) {
        ShopsModel item = ((ShopsAdapter) listView.getAdapter()).getItem(position);
        SQLiteDatabase db = MainActivity.rootAcvitityInstance.getDbHelper().getWritableDatabase();
        if(!likedList.contains(item.getId())) {
            likedList.add(item.getId());

            ContentValues cv = new ContentValues();

            cv.put("shop_id", item.getId());
            cv.put("category_id", item.getCategoryId());
            cv.put("city_id", item.getCityId());
            cv.put("title", item.getTitle());
            cv.put("description", item.getDescription());
            cv.put("time", item.getTime());
            cv.put("lat", item.getLatitude());
            cv.put("lon", item.getLongitude());
            cv.put("street", item.getStreet());
            cv.put("phone", item.getPhone());
            cv.put("date_start", item.getDataStart());
            cv.put("date_stop", item.getDataStop());
            cv.put("updated_at", item.getUpdatedData());
            cv.put("rating", item.getRating());

            db.insert("likedList", null, cv);

        } else {
            likedList.remove(item.getId());
            db.execSQL("DELETE FROM likedList WHERE shop_id=" + item.getId() + ";");
        }

        db.close();

        ((ShopsAdapter) listView.getAdapter()).setLikedList(likedList);
        ((ShopsAdapter) listView.getAdapter()).notifyDataSetChanged();
    }
}