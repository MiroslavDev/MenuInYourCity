package com.miroslav.menuinyourcity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ShopsReviewsModel item = getItem(position);

        holder.name.setText(item.getName());
        holder.description.setText(item.getReview());

        return convertView;
    }

    private class ViewHolder {
        public TextView name;
        public TextView description;
    }

}