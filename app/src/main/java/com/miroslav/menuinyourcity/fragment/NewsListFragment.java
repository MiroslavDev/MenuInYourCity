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

    //public  static final String NEWS_ID = "news_id";

    private List<GetNewsModel> data;
    private ListView listView;

    public static NewsListFragment newInstance() {
        NewsListFragment fr = new NewsListFragment();
        Bundle arg = new Bundle();
        //arg.putLong(NEWS_ID, newsId);
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

        //Long id = getArguments().getLong(NEWS_ID);

        listView = (ListView) view.findViewById(R.id.frg_catalog_listview);
        listView.setAdapter(new NewsAdapter(getContext(), new ArrayList<GetNewsModel>()));
        listView.setOnItemClickListener(this);


        setupAB(getContext().getString(R.string.event_and_news));
        newRequest();

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

            }

            @Override
            public void onRequestSuccess(BaseGetNewsModel data) {
                if (!data.getError()) {
                    updaateAdapterData(data.getNewsModel());
                } else {
                    Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updaateAdapterData(List<GetNewsModel> data) {
        this.data = data;
        NewsAdapter adapter = (NewsAdapter) listView.getAdapter();
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GetNewsModel item = ((NewsAdapter) listView.getAdapter()).getItem(position);
        //TODO do valid data
        DetailNewsFragment fr = DetailNewsFragment.newInstance(item.getTitle(), item.getImageUrl(), item.getCreatedAt(), item.getDescription());
        ((MainActivity) getActivity()).replaceFragment(fr);
    }
}