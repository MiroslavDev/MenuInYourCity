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
import com.miroslav.menuinyourcity.request.GetNews.GetNewsModel;
import com.miroslav.menuinyourcity.request.GetShops.GetShopsModel;
import com.miroslav.menuinyourcity.request.URLHelper;

import java.util.List;

/**
 * Created by apple on 4/10/16.
 */
public class NewsAdapter extends ArrayAdapter<GetNewsModel> {

    public NewsAdapter(Context context, List<GetNewsModel> data) {
        super(context, R.layout.event_item, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.event_item_name);
            holder.data = (TextView) convertView.findViewById(R.id.event_item_data);
            holder.description = (TextView) convertView.findViewById(R.id.event_description);
            holder.image = (ImageView) convertView.findViewById(R.id.event_item_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GetNewsModel item = getItem(position);
        holder.name.setText(item.getTitle());
        //TODO set valid data
        holder.data.setText(item.getCreatedAt());
        holder.description.setText(item.getDescription());

        if(!item.getImageUrl().isEmpty())
            MainActivity.imageLoader.DisplayImage(URLHelper.imageDomain + item.getImageUrl(), holder.image);

        return convertView;
    }

    private class ViewHolder {
        public TextView name;
        public TextView data;
        public TextView description;
        public ImageView image;
    }
}
