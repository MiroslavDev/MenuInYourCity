package com.miroslav.menuinyourcity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.miroslav.menuinyourcity.request.GetShops.ShopsModel;

/**
 * Created by apple on 4/12/16.
 */
public class Utils {

    public static void hideKeyboard(Context context, View view) {
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
