package com.miroslav.menuinyourcity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crittercism.app.Crittercism;
import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.request.URLHelper;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by apple on 4/11/16.
 */
public class DetailNewsFragment extends BaseFragment {

    public  static final String ACTION_BAR_TITLE = "action_bar_title";
    public  static final String IMAGE_URL = "image_url";
    public  static final String DATA = "data";
    public  static final String DESCRIBE = "describe";

    private SimpleDateFormat formatStart = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private SimpleDateFormat formatEnd = new SimpleDateFormat("dd MMMM yyyy");

    public static DetailNewsFragment newInstance(String sbTitle, String imageUrl, String data, String describe) {
        DetailNewsFragment fr = new DetailNewsFragment();
        Bundle arg = new Bundle();
        arg.putString(ACTION_BAR_TITLE, sbTitle);
        arg.putString(IMAGE_URL, imageUrl);
        arg.putString(DATA, data);
        arg.putString(DESCRIBE, describe);
        fr.setArguments(arg);
        return fr;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_shares_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            Crittercism.initialize(getActivity(),
                    "9df78e5d8b3243289652e05a3de44c6000555300");
        } catch (NoClassDefFoundError e) {

        }
        String sbTitle = getArguments().getString(ACTION_BAR_TITLE);
        String imageUrl = getArguments().getString(IMAGE_URL);
        String data = getArguments().getString(DATA);
        String describe = getArguments().getString(DESCRIBE);

        view.findViewById(R.id.frg_shares_item_three_dots).setVisibility(View.GONE);

        ((TextView) view.findViewById(R.id.frg_shares_item_description)).setText(describe);

        ImageView image = (ImageView) view.findViewById(R.id.frg_shares_item_img);

        try {
            Date newDate = formatStart.parse(data);
            ((TextView) view.findViewById(R.id.frg_shares_item_title)).setText(formatEnd.format(newDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(!imageUrl.isEmpty())
            Picasso.with(getContext()).load(URLHelper.imageDomain + imageUrl).into(image);


        setupAB(sbTitle);
    }

    private void setupAB(String title) {
        ((MainActivity) getActivity()).setVisibleButtonBackInActBar();
        ((MainActivity) getActivity()).setTitleActBar(title);
    }
}
