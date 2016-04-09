package com.miroslav.menuinyourcity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.adapter.CatalogAdapter;
import com.miroslav.menuinyourcity.request.Categories.BaseCategoriesModel;
import com.miroslav.menuinyourcity.request.Categories.CategorieModel;
import com.miroslav.menuinyourcity.request.Categories.GetChildrenCategoriesRequest;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 4/8/16.
 */
public class CatalogFragment extends com.miroslav.menuinyourcity.fragment.BaseFragment {

    public  static final String PARENT_ID = "parent_id";

    private Long parentId;
    private ListView listView;
    private List<CategorieModel> categorieModelList;

    public static CatalogFragment newInstance(Long id) {
        CatalogFragment fr = new CatalogFragment();
        Bundle arg = new Bundle();
        arg.putLong(PARENT_ID, id);
        Log.d("parentId = ", id+"");
        fr.setArguments(arg);
        return fr;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        parentId = getArguments().getLong(CatalogFragment.PARENT_ID);
        Log.d("parentId = ", parentId+"");
        listView = (ListView) view.findViewById(R.id.frg_catalog_listview);
        listView.setAdapter(new CatalogAdapter(getContext(), new ArrayList<CategorieModel>()));
        setupActionBar();
    }

    private void setupActionBar() {
        ((MainActivity) getActivity()).showActBar();
        ((MainActivity) getActivity()).setVisibleButtonBackInActBar();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(categorieModelList == null) {
            categoriesRequest();
        } else {
            updaateAdapterData(categorieModelList);
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_subcategories, container, false);
    }

    private void categoriesRequest() {
        Log.d("parentId = ", parentId+"");
        GetChildrenCategoriesRequest request = new GetChildrenCategoriesRequest(parentId);
        spiceManager.execute(request, request.getResourceUri(), request.getCacheExpiryDuration(), new RequestListener<BaseCategoriesModel>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(BaseCategoriesModel baseCategoriesModel) {
                if (!baseCategoriesModel.getError()) {
                    updaateAdapterData(baseCategoriesModel.getCategorieList());
                } else {
                    Toast.makeText(getContext(), baseCategoriesModel.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updaateAdapterData(List<CategorieModel> data) {
        categorieModelList = data;
        CatalogAdapter adapter = (CatalogAdapter) listView.getAdapter();
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }

}