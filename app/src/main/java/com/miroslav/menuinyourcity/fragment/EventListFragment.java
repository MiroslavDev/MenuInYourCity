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
import com.miroslav.menuinyourcity.adapter.EventAdapter;
import com.miroslav.menuinyourcity.request.GetEvents.BaseGetEventsModel;
import com.miroslav.menuinyourcity.request.GetEvents.GetEventModel;
import com.miroslav.menuinyourcity.request.GetEvents.GetEventRequest;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 4/10/16.
 */
public class EventListFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    public  static final String EVENT_ID = "event_id";

    private List<GetEventModel> data;
    private ListView listView;

    public static EventListFragment newInstance() {
        EventListFragment fr = new EventListFragment();
        Bundle arg = new Bundle();
        //arg.putLong(EVENT_ID, id);
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

        //Long id = getArguments().getLong(EVENT_ID);

        listView = (ListView) view.findViewById(R.id.frg_catalog_listview);
        listView.setAdapter(new EventAdapter(getContext(), new ArrayList<GetEventModel>()));
        listView.setOnItemClickListener(this);


        setupAB(getContext().getString(R.string.event_and_news));
        eventsRequest();

    }

    private void setupAB(String title) {
        ((MainActivity) getActivity()).setVisibleButtonBackInActBar();
        ((MainActivity) getActivity()).setTitleActBar(title);
    }

    private void eventsRequest() {
        GetEventRequest request = new GetEventRequest();
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

    private void updaateAdapterData(List<GetEventModel> data) {
        this.data = data;
        EventAdapter adapter = (EventAdapter) listView.getAdapter();
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}