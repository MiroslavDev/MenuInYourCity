package com.miroslav.menuinyourcity.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.Model;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.Utils;
import com.miroslav.menuinyourcity.dialogs.AttentionDialog;
import com.miroslav.menuinyourcity.dialogs.AttentionResultableDialog;
import com.miroslav.menuinyourcity.dialogs.LoadingDialog;
import com.miroslav.menuinyourcity.request.Reviews.StoreReviewModel;
import com.miroslav.menuinyourcity.request.Reviews.StoreReviewRequest;
import com.miroslav.menuinyourcity.request.StoreUsers.BaseStoreUsersModel;
import com.miroslav.menuinyourcity.request.StoreUsers.PostStoreUsersRequest;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by apple on 4/25/16.
 */
public class AddReviewFragment extends BaseFragment implements DialogInterface.OnClickListener{

    public  static final String SHOP_ID_KEY = "shop_id";

    private EditText name;
    private EditText phone;
    private EditText desctiption;
    private RatingBar ratingBar;
    private String shopId;
    private LoadingDialog loadingDialog;

    public static AddReviewFragment newInstance(String shopId) {
        AddReviewFragment fr = new AddReviewFragment();
        Bundle arg = new Bundle();
        arg.putString(SHOP_ID_KEY, shopId);
        fr.setArguments(arg);
        return fr;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_give_review, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shopId = getArguments().getString(SHOP_ID_KEY);
        
        name = (EditText) view.findViewById(R.id.frg_give_review_name);
        phone = (EditText) view.findViewById(R.id.frg_give_review_phone);
        desctiption = (EditText) view.findViewById(R.id.frg_give_review_description);
        ratingBar = (RatingBar) view.findViewById(R.id.frg_give_review_rating_bar);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReviewRequest();
            }
        });

        setupAB();
    }

    private void addReviewRequest() {
        loadingDialog = new LoadingDialog();
        loadingDialog.setCancelable(false);
        loadingDialog.show(getFragmentManager(), null);
        Utils.hideKeyboard(getContext(), getActivity().getCurrentFocus());

        StoreReviewRequest request = new StoreReviewRequest(
                name.getText().toString(),
                phone.getText().toString(),
                String.valueOf(ratingBar.getRating()),
                shopId,
                desctiption.getText().toString());
        spiceManager.execute(request, request.getResourceUri(), request.getCacheExpiryDuration(), new RequestListener<StoreReviewModel>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                loadingDialog.dismiss();
            }

            @Override
            public void onRequestSuccess(StoreReviewModel data) {
                loadingDialog.dismiss();
                if (data.getError()) {
                    AttentionDialog resultableDialog = new AttentionDialog();
                    resultableDialog.setCancelable(false);
                    resultableDialog.setMessage(data.getMessage().equals("valid") ? getContext().getString(R.string.review_is_not_left_fill_all_field): getErrorMessage(data.getMessage()));
                    resultableDialog.show(getFragmentManager(), null);
                } else {
                    AttentionResultableDialog resultableDialog = new AttentionResultableDialog();
                    resultableDialog.setCancelable(false);
                    resultableDialog.setMessage(getContext().getString(R.string.review_is_left));
                    resultableDialog.setCallback(AddReviewFragment.this);
                    resultableDialog.show(getFragmentManager(), null);
                }

            }
        });
    }

    private String getErrorMessage(String error) {
        if(error.equals("denied access for repeat store review"))
            return getString(R.string.error_there_is_review);

        return getString(R.string.review_is_not_left);
    }

    private void setupAB() {
        ((MainActivity) getActivity()).setVisibleButtonBackInActBar();
        ((MainActivity) getActivity()).setTitleActBar(getContext().getString(R.string.add_review));
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        getActivity().onBackPressed();
    }
}