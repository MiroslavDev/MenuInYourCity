package com.miroslav.menuinyourcity.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import com.miroslav.menuinyourcity.R;

/**
 * Created by apple on 5/4/16.
 */
public class LoadingDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LayoutInflater.from(getContext()).inflate(R.layout.loading_dialog, null)).create();
        return builder.create();
    }
}