package com.miroslav.menuinyourcity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.request.Categories.CategorieModel;

import java.util.List;

/**
 * Created by apple on 4/8/16.
 */
public class CatalogAdapter extends ArrayAdapter<CategorieModel> {

    public CatalogAdapter(Context context, List<CategorieModel> data) {
        super(context, R.layout.canalog_item, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.canalog_item, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.catalog_item_name);
            holder.count = (TextView) convertView.findViewById(R.id.catalog_item_count);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(getItem(position).getName());
        holder.count.setText(getItem(position).getParentId());

        return convertView;
    }

    private class ViewHolder {
        public TextView name;
        public TextView count;
    }

}