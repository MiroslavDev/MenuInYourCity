package com.miroslav.menuinyourcity.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crittercism.app.Crittercism;
import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.adapter.TaxiAdapter;
import com.miroslav.menuinyourcity.dialogs.AttentionDialog;
import com.miroslav.menuinyourcity.request.PostResponse;
import com.miroslav.menuinyourcity.request.Taxi.BaseTaxiModel;
import com.miroslav.menuinyourcity.request.Taxi.GetTaxiRequest;
import com.miroslav.menuinyourcity.request.Taxi.StoreTaxiRatingRequest;
import com.miroslav.menuinyourcity.request.Taxi.TaxiModel;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 03.08.16.
 */
public class TaxiFragment  extends BaseFragment implements TaxiAdapter.Callback {

    public static final String TAG = "TaxiFragment";

    private ListView listView;
    private ProgressBar progressBar;
    private TextView labelDuringEmptyData;

    public static TaxiFragment newInstance() {
        return new TaxiFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            Crittercism.initialize(getActivity(),
                    "9df78e5d8b3243289652e05a3de44c6000555300");
        } catch (NoClassDefFoundError e) {

        }
        labelDuringEmptyData = (TextView) view.findViewById(R.id.shop_item_label_during_empty_data);
        labelDuringEmptyData.setText(R.string.taxi_not_found);
        labelDuringEmptyData.setVisibility(View.VISIBLE);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        listView = (ListView) view.findViewById(R.id.frg_catalog_listview);
        listView.setAdapter(new TaxiAdapter(getContext(), new ArrayList<TaxiModel>(), this));
        setupAB();
    }

    private void setupAB() {
        ((MainActivity) getActivity()).setVisibleButtonBackInActBar();
        ((MainActivity) getActivity()).setTitleActBar(getString(R.string.taxi));
    }

    @Override
    public void onStart() {
        super.onStart();
        taxiRequest();
    }

    public void taxiRequest() {
        progressBar.setVisibility(View.VISIBLE);
        labelDuringEmptyData.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        GetTaxiRequest request = new GetTaxiRequest();
        spiceManager.execute(request, request.getResourceUri(), request.getCacheExpiryDuration(), new RequestListener<BaseTaxiModel>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRequestSuccess(BaseTaxiModel data) {
                progressBar.setVisibility(View.GONE);
                if (!data.getError()) {
                    updaateAdapterData(data.getTaxiList());
                } else {
                    progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void ratingRequest(Long taxiId, @IntRange(from=1,to=10) Integer rating) {
        StoreTaxiRatingRequest request = new StoreTaxiRatingRequest(taxiId, rating);
        spiceManager.execute(request, request.getResourceUri(), request.getCacheExpiryDuration(), new RequestListener<PostResponse>() {

            @Override
            public void onRequestFailure(SpiceException spiceException) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRequestSuccess(PostResponse data) {
                if (!data.getError()) {
                    AttentionDialog resultableDialog = new AttentionDialog();
                    resultableDialog.setMessage(getContext().getString(R.string.review_is_left));
                    resultableDialog.show(getFragmentManager(), null);
                    taxiRequest();
                } else {
                    progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    if(data.getMessage().equals("Your voted before to this taxi")) {
                        AttentionDialog resultableDialog = new AttentionDialog();
                        resultableDialog.setMessage(getContext().getString(R.string.feedback_already_exist));
                        resultableDialog.show(getFragmentManager(), null);
                    } else {
                        AttentionDialog resultableDialog = new AttentionDialog();
                        resultableDialog.setMessage(getContext().getString(R.string.feedback_not_left));
                        resultableDialog.show(getFragmentManager(), null);
                    }
                }
            }
        });
    }

    @Override
    public void setRating(final TaxiModel taxiModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View ratingView = getActivity().getLayoutInflater().inflate(R.layout.rating_dialog, null);

        builder.setMessage("Оцените такси")
                .setView(ratingView)
                .setCancelable(false)
                .setPositiveButton("Оценить",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Integer rating = (int) ((RatingBar) ratingView.findViewById(R.id.rating_bar)).getRating();
                                ratingRequest(taxiModel.getId(), rating);
                            }
                        })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert = builder.create();
        alert.show();


    }

    @Override
    public void callToTaxi(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
        startActivity(intent);
    }

    private void updaateAdapterData(List<TaxiModel> data) {
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        TaxiAdapter adapter = (TaxiAdapter) listView.getAdapter();
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();

        if(data.isEmpty())
            labelDuringEmptyData.setVisibility(View.VISIBLE);
    }
}