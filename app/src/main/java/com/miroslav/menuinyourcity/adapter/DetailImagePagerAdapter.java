package com.miroslav.menuinyourcity.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.request.GetShops.ShopsPhotosModel;
import com.miroslav.menuinyourcity.request.URLHelper;
import com.miroslav.menuinyourcity.view.MySlider.SliderAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by apple on 5/8/16.
 */
public class DetailImagePagerAdapter extends SliderAdapter {

    private Context context;
    private List<ShopsPhotosModel> list;
    private PhotoViewAttacher.OnViewTapListener listener;
    private Boolean isFullInformation = false;
    private float width, height;
    private boolean isDispath = false;

    public DetailImagePagerAdapter(Context context, List<ShopsPhotosModel> list, PhotoViewAttacher.OnViewTapListener listener, int width, int height) {
        super(context);
        this.context = context;
        this.list = list;
        this.listener = listener;

        this.width = width * 5;
        this.height = height * 5;//context.getResources().getDimension(R.dimen.height_present_images);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void addAll(List<ShopsPhotosModel> list) {
        this.list.addAll(list);
    }

    public void setFullInformation(Boolean fullInformation) {
        isFullInformation = fullInformation;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        //photoView.setBlockedScrollView(isDispath);

        photoView.setTag(position);
        photoView.setScaleType(isFullInformation ? ImageView.ScaleType.FIT_CENTER : ImageView.ScaleType.CENTER_CROP);
        //photoView.setZoomable(isFullInformation);
        ShopsPhotosModel item = list.get(position);

        Picasso.with(context).load(URLHelper.imageDomain + item.getImage()).resize((int) width, (int) height).into(photoView);
        container.addView(photoView, ViewPager.LayoutParams.MATCH_PARENT,
                ViewPager.LayoutParams.MATCH_PARENT);

        //photoView.setOnPhotoTapListener(listener);
        photoView.setOnViewTapListener(listener);
        container.setBackgroundColor(context.getResources().getColor(R.color.slider_background));
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public ShopsPhotosModel getItem (int position) {
        return list.get(position);
    }
}