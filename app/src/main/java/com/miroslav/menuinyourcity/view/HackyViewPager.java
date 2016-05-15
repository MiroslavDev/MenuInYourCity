package com.miroslav.menuinyourcity.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.miroslav.menuinyourcity.view.MySlider.Tricks.ViewPagerEx;

/**
 * Created by apple on 5/8/16.
 */
public class HackyViewPager extends ViewPagerEx {

    private static final String TAG = "HackyViewPager";

    private Boolean isBlockedScrollView = false;

    public Boolean getBlockedScrollView() {
        return isBlockedScrollView;
    }

    public void setBlockedScrollView(Boolean blockedScrollView) {
        isBlockedScrollView = blockedScrollView;
    }


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

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if(isBlockedScrollView)
//            return true;
//        return super.dispatchTouchEvent(ev);
//    }
}