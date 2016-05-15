package com.miroslav.menuinyourcity.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.miroslav.menuinyourcity.R;

/**
 * Created by apple on 5/4/16.
 */
public class AttentionResultableDialog  extends DialogFragment {

    private DialogInterface.OnClickListener listener;
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCallback(DialogInterface.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton(R.string.ok, listener);
        return builder.create();
    }
}