package com.miroslav.menuinyourcity.view.MySlider.Transformers;

import android.view.View;

/**
 * Created by apple on 5/5/16.
 */
public class DefaultTransformer extends BaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
    }

    @Override
    public boolean isPagingEnabled() {
        return true;
    }

}

