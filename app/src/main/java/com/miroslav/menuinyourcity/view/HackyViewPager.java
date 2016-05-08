package com.miroslav.menuinyourcity.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by apple on 5/8/16.
 */
public class HackyViewPager extends ViewPager {

    private static final String TAG = "HackyViewPager";


    public HackyViewPager(Context context)
    {
        super(context);
    }

    public HackyViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        try {
            return super.onInterceptTouchEvent(ev);
        }
        catch (IllegalArgumentException e) {
            Log.e(TAG, "IllegalArgumentException");
        }
        catch (ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, "ArrayIndexOutOfBoundsException");
        }
        return false;
    }
}