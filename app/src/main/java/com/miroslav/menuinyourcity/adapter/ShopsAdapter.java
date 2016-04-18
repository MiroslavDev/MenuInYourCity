package com.miroslav.menuinyourcity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.request.GetShops.GetShopsModel;
import com.miroslav.menuinyourcity.request.URLHelper;

import java.util.List;

/**
 * Created by apple on 4/10/16.
 */
public class ShopsAdapter extends ArrayAdapter<GetShopsModel> {

    public ShopsAdapter(Context context, List<GetShopsModel> data) {
        super(context, R.layout.shop_item, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shop_item, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.shop_item_name);
            holder.address = (TextView) convertView.findViewById(R.id.shop_item_street);
            holder.timeWork = (TextView) convertView.findViewById(R.id.shop_item_work_time);
            holder.ratting = (TextView) convertView.findViewById(R.id.shop_item_rating);
            holder.image = (ImageView) convertView.findViewById(R.id.shop_item_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GetShopsModel item = getItem(position);
        holder.name.setText(item.getTitle());
        holder.address.setText(item.getStreet());
        holder.timeWork.setText(item.getTime());
        holder.ratting.setText(getContext().getString(R.string.rating) + " 7.9");

        if(item.getPhotos() != null && !item.getPhotos().isEmpty())
            MainActivity.imageLoader.DisplayImage(URLHelper.imageDomain + item.getPhotos().get(0).getImage(), holder.image);

        return convertView;
    }

    private class ViewHolder {
        public TextView name;
        public TextView address;
        public TextView timeWork;
        public TextView ratting;
        public ImageView image;
    }
}