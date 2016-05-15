package com.miroslav.menuinyourcity.view.MySlider.Transformers;

import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by apple on 5/5/16.
 */
public class CubeInTransformer extends BaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        // Rotate the fragment on the left or right edge
        ViewHelper.setPivotX(view,position > 0 ? 0 : view.getWidth());
        ViewHelper.setPivotY(view,0);
        ViewHelper.setRotation(view,-90f * position);
    }

    @Override
    public boolean isPagingEnabled() {
        return true;
    }

}
