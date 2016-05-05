package com.miroslav.menuinyourcity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by apple on 4/27/16.
 */
public class ListViewOnFullScreen extends ListView {

    public ListViewOnFullScreen(Context context) {
        super(context);
    }

    public ListViewOnFullScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewOnFullScreen(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 4, MeasureSpec.AT_MOST));
    }
}
