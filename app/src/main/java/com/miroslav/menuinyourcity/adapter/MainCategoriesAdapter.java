package com.miroslav.menuinyourcity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.request.Categories.CategorieModel;
import com.miroslav.menuinyourcity.request.ChildrenCategories.GetChildrenCategoriesModel;
import com.miroslav.menuinyourcity.request.URLHelper;

import java.util.List;

/**
 * Created by apple on 4/6/16.
 */
public class MainCategoriesAdapter extends BaseAdapter{
    private Context context;
    private List<GetChildrenCategoriesModel> data;

    public MainCategoriesAdapter(Context context, List<GetChildrenCategoriesModel> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.main_categorie_item, parent, false);

            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.frg_main_categorie_item_img);
            holder.name = (TextView) convertView.findViewById(R.id.frg_main_categorie_item_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GetChildrenCategoriesModel item = data.get(position);

        MainActivity.imageLoader.DisplayImage(URLHelper.imageDomain + item.getImageUrl(),holder.image);

        holder.name.setText(item.getName());

        return convertView;
    }

    public void addAll(List<GetChildrenCategoriesModel> data) {
        this.data.addAll(data);
    }

    public void clear() {
        this.data.clear();
    }

    private class ViewHolder {
        public ImageView image;
        public TextView name;
    }
}
