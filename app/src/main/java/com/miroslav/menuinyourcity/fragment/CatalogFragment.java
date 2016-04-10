package com.miroslav.menuinyourcity.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.Model;
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
        listView.setAdapter(new CatalogAdapter(getContext(), new ArrayList<GetChildrenCategoriesModel>()));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

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

            }

            @Override
            public void onRequestSuccess(BaseChildrenCategoriesModel data) {
                if (!data.getError()) {
                    updaateAdapterData(data.getCategorieList());
                } else {
                    Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updaateAdapterData(List<GetChildrenCategoriesModel> data) {
        categorieModelList = data;
        CatalogAdapter adapter = (CatalogAdapter) listView.getAdapter();
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CatalogAdapter adapter = (CatalogAdapter) listView.getAdapter();
        //TODO change to getId()
        BaseFragment fr = ShopListFragment.newInstance(Long.parseLong(adapter.getItem(position).getParentId()), adapter.getItem(position).getName());
        ((MainActivity) getActivity()).replaceFragment(fr);
    }
}