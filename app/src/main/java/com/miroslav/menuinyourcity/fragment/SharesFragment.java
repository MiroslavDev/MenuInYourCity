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
import com.miroslav.menuinyourcity.adapter.SharesAdapter;
import com.miroslav.menuinyourcity.dialogs.AttentionDialog;
import com.miroslav.menuinyourcity.request.FollowCategory.BaseFollowCategoryModel;
import com.miroslav.menuinyourcity.request.FollowCategory.FollowCategoryRequest;
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
public class SharesFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    public  static final String TITLE = "title";
    public  static final String IS_FOLLOW_KEY = "is_follow_key";
    public  static final String CATEGORY_ID = "category_id";

    private View followBtn;
    private ListView listView;
    private Long categoryId;

    public static SharesFragment newInstance(Long id, Boolean isFollow) {
        SharesFragment fr = new SharesFragment();
        Bundle arg = new Bundle();
        arg.putLong(CATEGORY_ID, id);
        arg.putBoolean(IS_FOLLOW_KEY, isFollow);
        //arg.putString(TITLE, title);
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
        Boolean isFollow = arg.getBoolean(IS_FOLLOW_KEY);
        //String title = getArguments().getString(TITLE);

        followBtn = view.findViewById(R.id.frg_shares_btn_subscribe);
        followBtn.setEnabled(!isFollow);
        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followBtn.setEnabled(false);
                followShares(categoryId);
            }
        });

        listView = (ListView) view.findViewById(R.id.frg_shares_list_view);
        listView.setAdapter(new SharesAdapter(getContext(), new ArrayList<GetEventModel>()));
        listView.setOnItemClickListener(this);

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
                    AttentionDialog dl = new AttentionDialog();
                    dl.setMessage(getContext().getString(R.string.you_follow_the_shares));
                    dl.show(getFragmentManager(), null);
                } else {
                    //Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updaateAdapterData(List<GetEventModel> data) {
        SharesAdapter adapter = (SharesAdapter) listView.getAdapter();
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}