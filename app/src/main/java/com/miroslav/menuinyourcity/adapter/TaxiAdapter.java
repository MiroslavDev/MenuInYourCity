package com.miroslav.menuinyourcity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.request.Taxi.TaxiModel;

import java.util.List;
import java.util.Locale;

/**
 * Created by apple on 03.08.16.
 */
public class TaxiAdapter extends ArrayAdapter<TaxiModel> {

    private Callback callback;

    public TaxiAdapter(Context context, List<TaxiModel> data, Callback callback) {
        super(context, R.layout.shop_item, data);
        this.callback = callback;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.taxi_item, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.taxi_name);
            holder.number = (TextView) convertView.findViewById(R.id.taxi_number);
            holder.ratting = (TextView) convertView.findViewById(R.id.taxi_rating);
            holder.call = (ImageView) convertView.findViewById(R.id.taxi_call);
            holder.ratingStar = (ImageView) convertView.findViewById(R.id.taxi_rating_star);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final TaxiModel item = getItem(position);
        holder.name.setText(item.getName());
        holder.number.setText(item.getPhone());
        String ratingS = String.format( Locale.US, "%.2f", item.getRating());
        holder.ratting.setText(getContext().getString(R.string.rating) + " " +ratingS);
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null)
                    callback.callToTaxi(item.getPhone());
            }
        });
        holder.ratingStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null)
                    callback.setRating(item);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        public TextView name;
        public TextView number;
        public TextView ratting;
        public ImageView call;
        public ImageView ratingStar;
    }

    public interface Callback {
        void callToTaxi(String number);
        void setRating(TaxiModel taxiModel);
    }
}