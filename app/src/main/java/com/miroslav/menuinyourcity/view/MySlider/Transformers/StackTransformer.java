package com.miroslav.menuinyourcity.view.MySlider.Transformers;

import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by apple on 5/5/16.
 */
public class StackTransformer extends BaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        ViewHelper.setTranslationX(view,position < 0 ? 0f : -view.getWidth() * position);
    }

}
