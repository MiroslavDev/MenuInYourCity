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
import com.miroslav.menuinyourcity.request.GetShops.ShopsModel;
import com.miroslav.menuinyourcity.request.URLHelper;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Created by apple on 4/10/16.
 */
public class ShopsAdapter extends ArrayAdapter<ShopsModel> {

    private OnLikedImageClickListener likedImageListener;
    private float width, height;

    private Set<Long> likedList = new HashSet<>();

    public ShopsAdapter(Context context, List<ShopsModel> data, OnLikedImageClickListener likedImageListener, Set<Long> likedList) {
        super(context, R.layout.shop_item, data);
        this.likedImageListener = likedImageListener;
        this.likedList = likedList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shop_item, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.shop_item_name);
            holder.address = (TextView) convertView.findViewById(R.id.shop_item_street);
            holder.timeWork = (TextView) convertView.findViewById(R.id.shop_item_work_time);
            holder.ratting = (TextView) convertView.findViewById(R.id.shop_item_rating);
            holder.image = (ImageView) convertView.findViewById(R.id.shop_item_image);
            holder.likedImage = (ImageView) convertView.findViewById(R.id.shop_item_favourits);

            width = getContext().getResources().getDimension(R.dimen.card_height);
            height = getContext().getResources().getDimension(R.dimen.card_height);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ShopsModel item = getItem(position);
        holder.name.setText(item.getTitle());
        holder.address.setText(item.getStreet());
        holder.timeWork.setText(item.getTime());
        String ratingS = String.format( Locale.US, "%.2f", item.getRating());
        holder.ratting.setText(getContext().getString(R.string.rating) + ratingS);
        holder.likedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(likedImageListener != null)
                    likedImageListener.onLikedImageClick(position);
            }
        });
        if(likedList == null || likedList.contains(item.getId())) {
            holder.likedImage.setImageResource(R.drawable.ic_star_enable);
        } else {
            holder.likedImage.setImageResource(R.drawable.ic_star_inactive);
        }

        if(item.getPhotos() != null && !item.getPhotos().isEmpty())
            Picasso.with(getContext()).load(URLHelper.imageDomain + item.getPhotos().get(0).getImage()).resize((int) width, (int) height).into(holder.image);

        return convertView;
    }

    public void setLikedList(Set<Long> likedList) {
        this.likedList = likedList;
    }

    private class ViewHolder {
        public TextView name;
        public TextView address;
        public TextView timeWork;
        public TextView ratting;
        public ImageView image;
        public ImageView likedImage;
    }

    public interface OnLikedImageClickListener {
        void onLikedImageClick(int position);
    }
}