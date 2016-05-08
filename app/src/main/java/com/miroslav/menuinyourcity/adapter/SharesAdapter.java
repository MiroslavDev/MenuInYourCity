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

    private SharesCallback callback;

    public SharesAdapter(Context context, List<GetEventModel> data, SharesCallback callback) {
        super(context, R.layout.event_item, data);

        this.callback = callback;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shares_item, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.frg_shares_item_title);
            holder.description = (TextView) convertView.findViewById(R.id.frg_shares_item_description);
            holder.image = (ImageView) convertView.findViewById(R.id.frg_shares_item_img);
            holder.threeDots = (TextView) convertView.findViewById(R.id.frg_shares_item_three_dots);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GetEventModel item = getItem(position);
        holder.name.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.threeDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onItemThreeDotsClick(position);
            }
        });

        if (!item.getImageUrl().isEmpty())
            MainActivity.imageLoader.DisplayImage(URLHelper.imageDomain + item.getImageUrl(), holder.image);

        return convertView;
    }

    private class ViewHolder {
        public TextView name;
        public TextView description;
        public ImageView image;
        public TextView threeDots;
    }

    public interface SharesCallback{
        void onItemThreeDotsClick(int position);
    }
}

