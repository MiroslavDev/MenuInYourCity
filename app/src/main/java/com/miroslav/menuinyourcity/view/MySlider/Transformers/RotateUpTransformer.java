package com.miroslav.menuinyourcity.view.MySlider.Transformers;

import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by apple on 5/5/16.
 */
public class RotateUpTransformer extends BaseTransformer {

    private static final float ROT_MOD = -15f;

    @Override
    protected void onTransform(View view, float position) {
        final float width = view.getWidth();
        final float rotation = ROT_MOD * position;

        ViewHelper.setPivotX(view,width * 0.5f);
        ViewHelper.setPivotY(view,0f);
        ViewHelper.setTranslationX(view,0f);
        ViewHelper.setRotation(view,rotation);
    }

    @Override
    protected boolean isPagingEnabled() {
        return true;
    }

}
