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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.miroslav.menuinyourcity.DBHelper;
import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.Model;
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

    private Long id;
    private List<ShopsModel> data;
    private ListView listView;
    private String title;
    private Set<Long> likedList = new HashSet<>();
    private ProgressBar progressBar;

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

        id = getArguments().getLong(SHOP_ID);
        title = getArguments().getString(TITLE);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        listView = (ListView) view.findViewById(R.id.frg_catalog_listview);
        listView.setVisibility(View.GONE);
        listView.setAdapter(new ShopsAdapter(getContext(), new ArrayList<ShopsModel>(), this, null));
        listView.setOnItemClickListener(this);

        setupAB();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(data == null)
            shopsRequest(id);
        else
            updaateAdapterData(data);
    }

    private void fillLikedList() {
        SQLiteDatabase db = ((MainActivity) getActivity()).getDbHelper().getWritableDatabase();
        Cursor c = db.query("likedList", null, null, null, null, null, null);
        likedList.clear();

        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("shop_id");

            do {
                likedList.add(c.getLong(idColIndex));
            } while (c.moveToNext());
        }

        c.close();

        ((ShopsAdapter)listView.getAdapter()).setLikedList(likedList);
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
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onRequestSuccess(BaseGetShopsModel data) {
                if (!data.getError()) {
                    updaateAdapterData(data.getShopsModel());
                } else {
                    progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void updaateAdapterData(List<ShopsModel> data) {
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        this.data = data;
        ShopsAdapter adapter = (ShopsAdapter) listView.getAdapter();
        adapter.clear();
        fillLikedList();
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
        SQLiteDatabase db = ((MainActivity) getActivity()).getDbHelper().getWritableDatabase();
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
            cv.put("imageURL", item.getPhotos().size() > 0 ? item.getPhotos().get(0).getImage() : "");
            cv.put("category_name", title);

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