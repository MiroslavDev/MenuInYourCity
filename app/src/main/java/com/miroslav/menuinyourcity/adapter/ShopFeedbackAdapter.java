package com.miroslav.menuinyourcity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.request.GetShops.ShopsReviewsModel;

import java.util.List;

/**
 * Created by apple on 4/17/16.
 */
public class ShopFeedbackAdapter extends ArrayAdapter<ShopsReviewsModel> {

    public ShopFeedbackAdapter(Context context, List<ShopsReviewsModel> data) {
        super(context, R.layout.shop_review_item, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shop_review_item, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.frg_shop_review_name);
            holder.description = (TextView) convertView.findViewById(R.id.frg_shop_review_description);

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
        public ImageView ratingBar[] = new ImageView[10];
    }
}