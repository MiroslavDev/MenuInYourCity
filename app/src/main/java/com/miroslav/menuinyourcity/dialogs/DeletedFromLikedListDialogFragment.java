package com.miroslav.menuinyourcity.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.R;

/**
 * Created by apple on 4/24/16.
 */
public class DeletedFromLikedListDialogFragment extends DialogFragment {

    private Long id;
    private DialogCallback listener;

    public interface DialogCallback {
        void onResultFromDLG(Long id);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_from_liked_list)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onResultFromDLG(DeletedFromLikedListDialogFragment.this.id);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    public void setShopId(Long id) {
        this.id = id;
    }

    public void setListener(DialogCallback listener) {
        this.listener = listener;
    }
}
