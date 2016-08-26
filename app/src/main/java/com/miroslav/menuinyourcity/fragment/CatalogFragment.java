package com.miroslav.menuinyourcity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crittercism.app.Crittercism;
import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.adapter.CatalogAdapter;
import com.miroslav.menuinyourcity.request.ChildrenCategories.BaseChildrenCategoriesModel;
import com.miroslav.menuinyourcity.request.ChildrenCategories.GetChildrenCategoriesModel;
import com.miroslav.menuinyourcity.request.ChildrenCategories.GetChildrenCategoriesRequest;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 4/8/16.
 */
public class CatalogFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    public  static final String PARENT_ID = "parent_id";

    private Long parentId;
    private ListView listView;
    private List<GetChildrenCategoriesModel> categorieModelList;
    private ProgressBar progressBar;
    private TextView labelDuringEmptyData;

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
        try {
            Crittercism.initialize(getActivity(),
                    "9df78e5d8b3243289652e05a3de44c6000555300");
        } catch (NoClassDefFoundError e) {

        }
        parentId = getArguments().getLong(CatalogFragment.PARENT_ID);
        Log.d("parentId = ", parentId+"");
        labelDuringEmptyData = (TextView) view.findViewById(R.id.shop_item_label_during_empty_data);
        listView = (ListView) view.findViewById(R.id.frg_catalog_listview);
        listView.setVisibility(View.GONE);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        listView.setAdapter(new CatalogAdapter(getContext(), new ArrayList<GetChildrenCategoriesModel>()));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(categorieModelList == null) {
            categoriesRequest();
        } else {
            updaateAdapterData(categorieModelList);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_list_view, container, false);
    }

    private void categoriesRequest() {
        Log.d("parentId = ", parentId+"");
        GetChildrenCategoriesRequest request = new GetChildrenCategoriesRequest(parentId);
        spiceManager.execute(request, request.getResourceUri(), request.getCacheExpiryDuration(), new RequestListener<BaseChildrenCategoriesModel>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onRequestSuccess(BaseChildrenCategoriesModel data) {
                if (!data.getError()) {
                    updaateAdapterData(data.getCategorieList());
                } else {
                    progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updaateAdapterData(List<GetChildrenCategoriesModel> data) {
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        categorieModelList = data;
        CatalogAdapter adapter = (CatalogAdapter) listView.getAdapter();
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();

        if(data.isEmpty())
            labelDuringEmptyData.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CatalogAdapter adapter = (CatalogAdapter) listView.getAdapter();
        BaseFragment fr = ShopListFragment.newInstance(adapter.getItem(position).getId(), adapter.getItem(position).getName());
        ((MainActivity) getActivity()).replaceFragment(fr);
    }
}