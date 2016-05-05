package com.miroslav.menuinyourcity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.request.Cities.CitiesModel;

import java.util.List;

/**
 * Created by apple on 4/7/16.
 */
public class SpinnerAdapter extends ArrayAdapter<CitiesModel> {

    private int checkedPosition = 0;

    public SpinnerAdapter(Context context, List<CitiesModel> data) {
        super(context, R.layout.spinner_dropdown_item, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_dropdown_item, parent, false);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);

            holder = new ViewHolder();
            holder.checkedTextView = (CheckedTextView) convertView.findViewById(R.id.spinner_item_checked_tv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.checkedTextView.setText(getItem(position).getName());
        holder.checkedTextView.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                position == checkedPosition ? R.drawable.ic_check : 0,
                0);

        return convertView;
    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
    }

    private class ViewHolder {
        public CheckedTextView checkedTextView;
    }

}