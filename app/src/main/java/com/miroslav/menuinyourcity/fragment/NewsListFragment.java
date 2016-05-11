package com.miroslav.menuinyourcity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.adapter.NewsAdapter;
import com.miroslav.menuinyourcity.request.GetNews.BaseGetNewsModel;
import com.miroslav.menuinyourcity.request.GetNews.GetNewsModel;
import com.miroslav.menuinyourcity.request.GetNews.GetNewsRequest;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 4/10/16.
 */
public class NewsListFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    private ListView listView;
    private ProgressBar progressBar;
    private List<GetNewsModel> data;

    public static NewsListFragment newInstance() {
        NewsListFragment fr = new NewsListFragment();
        Bundle arg = new Bundle();
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

        listView = (ListView) view.findViewById(R.id.frg_catalog_listview);
        listView.setAdapter(new NewsAdapter(getContext(), new ArrayList<GetNewsModel>()));
        listView.setOnItemClickListener(this);
        listView.setVisibility(View.GONE);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);


        setupAB(getContext().getString(R.string.event_and_news));
        newRequest();

    }

    @Override
    public void onResume() {
        super.onResume();
        if(data == null) {
            newRequest();
        } else {
            updaateAdapterData(data);
        }
    }

    private void setupAB(String title) {
        ((MainActivity) getActivity()).setVisibleButtonBackInActBar();
        ((MainActivity) getActivity()).setTitleActBar(title);
    }

    private void newRequest() {
        GetNewsRequest request = new GetNewsRequest();
        spiceManager.execute(request, request.getResourceUri(), request.getCacheExpiryDuration(), new RequestListener<BaseGetNewsModel>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onRequestSuccess(BaseGetNewsModel data) {
                if (!data.getError()) {
                    updaateAdapterData(data.getNewsModel());
                } else {
                    progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updaateAdapterData(List<GetNewsModel> data) {
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        this.data = data;
        NewsAdapter adapter = (NewsAdapter) listView.getAdapter();
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GetNewsModel item = ((NewsAdapter) listView.getAdapter()).getItem(position);
        DetailNewsFragment fr = DetailNewsFragment.newInstance(item.getTitle(), item.getImageUrl(), item.getCreatedAt(), item.getDescription());
        ((MainActivity) getActivity()).replaceFragment(fr);
    }
}