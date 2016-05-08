package com.miroslav.menuinyourcity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.adapter.SharesAdapter;
import com.miroslav.menuinyourcity.dialogs.AttentionDialog;
import com.miroslav.menuinyourcity.request.FollowCategory.BaseFollowCategoryModel;
import com.miroslav.menuinyourcity.request.FollowCategory.FollowCategoryRequest;
import com.miroslav.menuinyourcity.request.FollowCategory.UnfollowCategoryRequest;
import com.miroslav.menuinyourcity.request.GetEvents.BaseGetEventsModel;
import com.miroslav.menuinyourcity.request.GetEvents.GetEventByCategoryRequest;
import com.miroslav.menuinyourcity.request.GetEvents.GetEventModel;
import com.miroslav.menuinyourcity.view.ListViewOnFullScreen;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 4/8/16.
 */
public class SharesFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    public  static final String TITLE = "title";
    public  static final String IS_FOLLOW_KEY = "is_follow_key";
    public  static final String CATEGORY_ID = "category_id";

    private View followBtn;
    private SharesAdapter adapter;
    private Long categoryId;
    private Boolean isFollow = false;
    private TextView labelFollow;

    public static SharesFragment newInstance(Long id, Boolean isFollow) {
        SharesFragment fr = new SharesFragment();
        Bundle arg = new Bundle();
        arg.putLong(CATEGORY_ID, id);
        arg.putBoolean(IS_FOLLOW_KEY, isFollow);
        fr.setArguments(arg);
        return fr;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_shares, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arg = getArguments();
        categoryId = arg.getLong(CATEGORY_ID);
        isFollow = arg.getBoolean(IS_FOLLOW_KEY);

        followBtn = LayoutInflater.from(getContext()).inflate(R.layout.follow_button, null);
        labelFollow = (TextView) followBtn.findViewById(R.id.frg_shares_btn_subscribe_label);
        if(isFollow)
            labelFollow.setText(getString(R.string.unfollow_on_push));
        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFollow)
                    unfollowShares(categoryId);
                else
                    followShares(categoryId);
            }
        });

        ListView listView = (ListView) view.findViewById(R.id.frg_shares_list_view);
        adapter = new SharesAdapter(getContext(), new ArrayList<GetEventModel>());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.addHeaderView(followBtn);

        sharesRequest(categoryId);

    }

    private void sharesRequest(Long id) {
        GetEventByCategoryRequest request = new GetEventByCategoryRequest(id);
        spiceManager.execute(request, request.getResourceUri(), request.getCacheExpiryDuration(), new RequestListener<BaseGetEventsModel>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(BaseGetEventsModel data) {
                if (!data.getError()) {
                    updaateAdapterData(data.getEventsModel());
                } else {
                    Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void followShares(Long id) {
        FollowCategoryRequest request = new FollowCategoryRequest(id);
        spiceManager.execute(request, request.getResourceUri(), request.getCacheExpiryDuration(), new RequestListener<BaseFollowCategoryModel>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(BaseFollowCategoryModel data) {
                if (!data.getError()) {
                    isFollow = true;
                    labelFollow.setText(getString(R.string.unfollow_on_push));
                    AttentionDialog dl = new AttentionDialog();
                    dl.setMessage(getContext().getString(R.string.you_follow_the_shares));
                    dl.show(getFragmentManager(), null);
                }
            }
        });
    }

    private void unfollowShares(Long id) {
        UnfollowCategoryRequest request = new UnfollowCategoryRequest(id);
        spiceManager.execute(request, request.getResourceUri(), request.getCacheExpiryDuration(), new RequestListener<BaseFollowCategoryModel>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(BaseFollowCategoryModel data) {
                if (!data.getError()) {
                    isFollow = false;
                    labelFollow.setText(getString(R.string.subscribe_on_push));
                    AttentionDialog dl = new AttentionDialog();
                    dl.setMessage(getContext().getString(R.string.you_unfollow_the_shares));
                    dl.show(getFragmentManager(), null);
                }
            }
        });
    }

    private void updaateAdapterData(List<GetEventModel> data) {
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BaseFragment fr = DetailsShopFragment.newInstance(Long.parseLong(adapter.getItem(position).getShopId()), adapter.getItem(position).getTitle());
        ((MainActivity) getActivity()).replaceFragment(fr);
    }
}