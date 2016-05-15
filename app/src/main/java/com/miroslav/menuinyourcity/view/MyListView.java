package com.miroslav.menuinyourcity.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by apple on 5/13/16.
 */
public class MyListView extends ListView {

    private Boolean isBlockedScrollView = false;

    public Boolean getBlockedScrollView() {
        return isBlockedScrollView;
    }

    public void setBlockedScrollView(Boolean blockedScrollView) {
        isBlockedScrollView = blockedScrollView;
    }

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



}
