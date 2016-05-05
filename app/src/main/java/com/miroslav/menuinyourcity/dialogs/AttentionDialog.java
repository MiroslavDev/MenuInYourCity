package com.miroslav.menuinyourcity.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.miroslav.menuinyourcity.R;

/**
 * Created by apple on 4/28/16.
 */
public class AttentionDialog extends DialogFragment {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AttentionDialog.this.dismiss();
                    }
                });
        return builder.create();
    }
}