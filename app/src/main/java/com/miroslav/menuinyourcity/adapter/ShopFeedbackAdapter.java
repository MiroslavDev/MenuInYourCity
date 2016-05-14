package com.miroslav.menuinyourcity.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.request.GetShops.ShopsReviewsModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 4/17/16.
 */
public class ShopFeedbackAdapter extends ArrayAdapter<ShopsReviewsModel> {

    private Handler uihandler;
    private Map<Integer, Boolean> isMoreInformationList = new HashMap<>();

    public ShopFeedbackAdapter(Context context, List<ShopsReviewsModel> data, Handler handler) {
        super(context, R.layout.shop_review_item, data);

        this.uihandler = handler;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shop_review_item, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.frg_shop_review_name);
            holder.description = (TextView) convertView.findViewById(R.id.frg_shop_review_description);
            holder.moreInformationBtn = (TextView) convertView.findViewById(R.id.frg_details_shop_description_more);


            holder.ratingBar[0] = (ImageView) convertView.findViewById(R.id.star1);
            holder.ratingBar[1] = (ImageView) convertView.findViewById(R.id.star2);
            holder.ratingBar[2] = (ImageView) convertView.findViewById(R.id.star3);
            holder.ratingBar[3] = (ImageView) convertView.findViewById(R.id.star4);
            holder.ratingBar[4] = (ImageView) convertView.findViewById(R.id.star5);
            holder.ratingBar[5] = (ImageView) convertView.findViewById(R.id.star6);
            holder.ratingBar[6] = (ImageView) convertView.findViewById(R.id.star7);
            holder.ratingBar[7] = (ImageView) convertView.findViewById(R.id.star8);
            holder.ratingBar[8] = (ImageView) convertView.findViewById(R.id.star9);
            holder.ratingBar[9] = (ImageView) convertView.findViewById(R.id.star10);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ShopsReviewsModel item = getItem(position);

        holder.name.setText(item.getName());
        holder.description.setText(item.getReview());
        setRating(holder, (int) Double.parseDouble(item.getRating()));

        holder.moreInformationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.description.setMaxLines(Integer.MAX_VALUE);
                holder.moreInformationBtn.setVisibility(View.GONE);
                isMoreInformationList.put(position, true);
            }
        });

        if(uihandler != null)
            uihandler.post(new Runnable() {
            @Override
            public void run() {
                if(!isMoreInformationList.containsKey(position)) {
                    if (holder.description.getLineCount() <= 4) {
                        holder.moreInformationBtn.setVisibility(View.GONE);
                    } else {
                        holder.description.setMaxLines(4);
                        holder.moreInformationBtn.setVisibility(View.VISIBLE);
                        isMoreInformationList.put(position, false);
                    }
                } else {
                    if(!isMoreInformationList.get(position)) {
                        holder.description.setMaxLines(4);
                        holder.moreInformationBtn.setVisibility(View.VISIBLE);
                    } else {
                        holder.description.setMaxLines(Integer.MAX_VALUE);
                        holder.moreInformationBtn.setVisibility(View.GONE);
                    }
                }
            }
        });

        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMoreInformationList.containsKey(position) && isMoreInformationList.get(position)) {
                    holder.description.setMaxLines(4);
                    holder.moreInformationBtn.setVisibility(View.VISIBLE);
                    isMoreInformationList.put(position, false);
                }
            }
        });

        return convertView;
    }

    private void setRating(ViewHolder holder, int rating) {
        for(int i = 0; i < 10; ++i)
            holder.ratingBar[i].setVisibility(View.GONE);
        for(int i = 0; i < rating; ++i)
            holder.ratingBar[i].setVisibility(View.VISIBLE);
    }

    private class ViewHolder {
        public TextView name;
        public TextView description;
        public TextView moreInformationBtn;
        public ImageView ratingBar[] = new ImageView[10];
    }
}