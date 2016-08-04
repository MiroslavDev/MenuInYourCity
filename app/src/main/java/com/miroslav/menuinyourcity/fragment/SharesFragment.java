package com.miroslav.menuinyourcity.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 4/8/16.
 */
public class SharesFragment extends BaseFragment implements AdapterView.OnItemClickListener, SharesAdapter.SharesCallback {

    public static final String IS_FOLLOW_KEY = "is_follow_key";
    public static final String CATEGORY_ID = "category_id";

    private View followBtn;
    private SharesAdapter adapter;
    private Long categoryId;
    private Boolean isFollow = false;
    private TextView labelFollow;
    private ProgressBar progressBar;
    private ListView listView;
    private View header;
    private int headerHeight = 0;
    private Boolean isFirstVisible = true;
    private TextView labelDuringEmptyData;


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

        labelDuringEmptyData = (TextView) view.findViewById(R.id.shop_item_label_during_empty_data);
        followBtn = view.findViewById(R.id.frg_shares_btn_subscribe);
        labelFollow = (TextView) view.findViewById(R.id.frg_shares_btn_subscribe_label);
        Log.d("isFollow", " "+isFollow.toString());
        if (isFollow) {
            labelFollow.setBackgroundColor(getResources().getColor(R.color.disable_gray));
            labelFollow.setText(getString(R.string.unfollow_on_push));
            labelFollow.setTextColor(getResources().getColor(R.color.text_color_black));
        } else {
            labelFollow.setTextColor(getResources().getColor(R.color.text_color_white));
            labelFollow.setBackgroundColor(getResources().getColor(R.color.main_orange));
            labelFollow.setText(getString(R.string.subscribe_on_push));
        }
        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollow)
                    unfollowShares(categoryId);
                else
                    followShares(categoryId);
            }
        });

        listView = (ListView) view.findViewById(R.id.frg_shares_list_view);
        if (adapter == null)
            adapter = new SharesAdapter(getContext(), new ArrayList<GetEventModel>(), this, view.getHandler());
        header = LayoutInflater.from(getContext()).inflate(R.layout.follow_button, null);
        listView.addHeaderView(header);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setVisibility(View.GONE);

        followBtn.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        headerHeight = followBtn.getMeasuredHeight();

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if(!isFirstVisible) {
                    if (scrollState == 0)
                        ObjectAnimator.ofFloat(followBtn, "y", 0).setDuration(200).start();
                    else
                        ObjectAnimator.ofFloat(followBtn, "y", -headerHeight).setDuration(200).start();
                } else {
                    ObjectAnimator.ofFloat(followBtn, "y", 0).setDuration(200).start();
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isFirstVisible = firstVisibleItem == 0;
            }
        });

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        sharesRequest(categoryId);
    }

    private void sharesRequest(Long id) {
        GetEventByCategoryRequest request = new GetEventByCategoryRequest(id);
        spiceManager.execute(request, request.getResourceUri(), request.getCacheExpiryDuration(), new RequestListener<BaseGetEventsModel>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onRequestSuccess(BaseGetEventsModel data) {
                if (!data.getError()) {
                    updaateAdapterData(data.getEventsModel());
                } else {
                    progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void followShares(Long id) {
        progressBar.setVisibility(View.VISIBLE);
        FollowCategoryRequest request = new FollowCategoryRequest(id);
        spiceManager.execute(request, request.getResourceUri(), request.getCacheExpiryDuration(), new RequestListener<BaseFollowCategoryModel>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(BaseFollowCategoryModel data) {
                if (!data.getError()) {
                    progressBar.setVisibility(View.GONE);
                    labelFollow.setBackgroundColor(getResources().getColor(R.color.disable_gray));
                    labelFollow.setTextColor(getResources().getColor(R.color.text_color_black));
                    isFollow = true;
                    Log.d("isFollow", " "+isFollow.toString());
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
                    progressBar.setVisibility(View.GONE);
                    labelFollow.setBackgroundColor(getResources().getColor(R.color.main_orange));
                    labelFollow.setTextColor(getResources().getColor(R.color.text_color_white));
                    isFollow = false;
                    Log.d("isFollow", " "+isFollow.toString());
                    labelFollow.setText(getString(R.string.subscribe_on_push));
                    AttentionDialog dl = new AttentionDialog();
                    dl.setMessage(getContext().getString(R.string.you_unfollow_the_shares));
                    dl.show(getFragmentManager(), null);
                }
            }
        });
    }

    private void updaateAdapterData(List<GetEventModel> data) {
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();

        if(data.isEmpty())
            labelDuringEmptyData.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //BaseFragment fr = DetailsShopFragment.newInstance(Long.parseLong(adapter.getItem(position).getShopId()), adapter.getItem(position).getTitle());
        //((MainActivity) getActivity()).replaceFragment(fr);
    }

    @Override
    public void onItemThreeDotsClick(int position) {
        BaseFragment fr = DetailsShopFragment.newInstance(Long.parseLong(adapter.getItem(position).getShopId()), adapter.getItem(position).getTitle());
        ((MainActivity) getActivity()).replaceFragment(fr);
    }
}