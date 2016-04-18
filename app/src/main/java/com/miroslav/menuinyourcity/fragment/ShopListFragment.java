package com.miroslav.menuinyourcity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.adapter.CatalogAdapter;
import com.miroslav.menuinyourcity.adapter.ShopsAdapter;
import com.miroslav.menuinyourcity.request.GetShops.BaseGetShopsModel;
import com.miroslav.menuinyourcity.request.GetShops.GetShopsModel;
import com.miroslav.menuinyourcity.request.GetShops.GetShopsByCategoryRequest;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 4/10/16.
 */
public class ShopListFragment  extends BaseFragment implements AdapterView.OnItemClickListener{

    public  static final String TITLE = "title";
    public  static final String SHOP_ID = "parent_id";

    private List<GetShopsModel> data;
    private ListView listView;
    private String title;

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

        listView = (ListView) view.findViewById(R.id.frg_catalog_listview);
        listView.setAdapter(new ShopsAdapter(getContext(), new ArrayList<GetShopsModel>()));
        listView.setOnItemClickListener(this);


        setupAB();
        shopsRequest(id);

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
                    updaateAdapterData(data.getGetShopsModel());
                } else {
                    Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updaateAdapterData(List<GetShopsModel> data) {
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
}