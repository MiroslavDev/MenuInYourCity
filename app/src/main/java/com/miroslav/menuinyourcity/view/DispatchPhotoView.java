package com.miroslav.menuinyourcity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by apple on 5/14/16.
 */
public class DispatchPhotoView extends PhotoView {

    private Boolean isBlockedScrollView = false;

    public Boolean getBlockedScrollView() {
        return isBlockedScrollView;
    }

    public void setBlockedScrollView(Boolean blockedScrollView) {
        isBlockedScrollView = blockedScrollView;
    }

    public DispatchPhotoView(Context context) {
        super(context);
    }

    public DispatchPhotoView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public DispatchPhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(isBlockedScrollView)
            return true;
        return super.dispatchTouchEvent(ev);
    }
}
