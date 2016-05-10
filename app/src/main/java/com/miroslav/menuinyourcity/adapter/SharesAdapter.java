package com.miroslav.menuinyourcity.adapter;

import android.content.Context;
import android.os.Handler;
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
            holder.moreInformationBtn = (TextView) convertView.findViewById(R.id.frg_details_shop_description_more);

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

        holder.moreInformationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.description.setMaxLines(Integer.MAX_VALUE);
                holder.moreInformationBtn.setVisibility(View.GONE);
                isMoreInformationList.put(position, true);
            }
        });

        uihandler.post(new Runnable() {
            @Override
            public void run() {
                if(!isMoreInformationList.containsKey(position)) {
                    if (holder.description.getLineCount() <= 4) {
                        holder.moreInformationBtn.setVisibility(View.GONE);
                    } else {
                        holder.description.setMaxLines(4);
                        holder.moreInformationBtn.setVisibility(View.VISIBLE);
                    }
                    isMoreInformationList.put(position, false);
                }else {
                    if (holder.description.getLineCount() > 4 && !isMoreInformationList.get(position)) {
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

    private class ViewHolder {
        public TextView name;
        public TextView description;
        public ImageView image;
        public TextView threeDots;
        public TextView moreInformationBtn;
    }

    public interface SharesCallback{
        void onItemThreeDotsClick(int position);
    }
}

