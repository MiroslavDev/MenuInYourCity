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
import com.miroslav.menuinyourcity.request.GetEvents.GetEventModel;
import com.miroslav.menuinyourcity.request.URLHelper;

import java.util.List;

/**
 * Created by apple on 4/10/16.
 */
public class SharesAdapter extends ArrayAdapter<GetEventModel> {

    public SharesAdapter(Context context, List<GetEventModel> data) {
        super(context, R.layout.event_item, data);
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.shares_item, parent, false);

        holder = new ViewHolder();
        holder.name = (TextView) convertView.findViewById(R.id.frg_shares_item_title);
        holder.description = (TextView) convertView.findViewById(R.id.frg_shares_item_description);
        holder.image = (ImageView) convertView.findViewById(R.id.frg_shares_item_img);

        convertView.setTag(holder);
        } else {
        holder = (ViewHolder) convertView.getTag();
        }

        GetEventModel item = getItem(position);
        holder.name.setText(item.getTitle());
        holder.description.setText(item.getDescription());

        if(!item.getImageUrl().isEmpty())
            MainActivity.imageLoader.DisplayImage(URLHelper.imageDomain + item.getImageUrl(), holder.image);

        return convertView;
    }

    private class ViewHolder {
        public TextView name;
        public TextView description;
        public ImageView image;
    }
}

