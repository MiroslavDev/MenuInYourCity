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
import com.miroslav.menuinyourcity.request.GetEvents.GetEventModel;
import com.miroslav.menuinyourcity.request.URLHelper;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by apple on 4/10/16.
 */
public class SharesAdapter extends ArrayAdapter<GetEventModel> {

    private SharesCallback callback;
    private Handler uihandler;
    private Map<Integer, Boolean> isMoreInformationList = new HashMap<>();

    public SharesAdapter(Context context, List<GetEventModel> data, SharesCallback callback, Handler handler) {
        super(context, R.layout.event_item, data);

        this.uihandler = handler;
        this.callback = callback;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

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
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onItemThreeDotsClick(position);
            }
        });

        if (!item.getImageUrl().isEmpty())
            Picasso.with(getContext()).load(URLHelper.imageDomain + item.getImageUrl()).into(holder.image);

        if(uihandler != null)
        uihandler.post(new Runnable() {
            @Override
            public void run() {
                if(!isMoreInformationList.containsKey(position)) {
                    if (holder.description.getLineCount() > 4)
                        holder.description.setMaxLines(4);
                    isMoreInformationList.put(position, false);
                }else {
                    if (holder.description.getLineCount() > 4 && !isMoreInformationList.get(position)) {
                        holder.description.setMaxLines(4);
                    } else {
                        holder.description.setMaxLines(Integer.MAX_VALUE);
                    }
                }
            }
        });

        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMoreInformationList.containsKey(position)) {
                    if(isMoreInformationList.get(position)) {
                        holder.description.setMaxLines(4);
                        isMoreInformationList.put(position, false);
                    } else {
                        holder.description.setMaxLines(Integer.MAX_VALUE);
                        isMoreInformationList.put(position, true);
                    }
                }
            }
        });

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

