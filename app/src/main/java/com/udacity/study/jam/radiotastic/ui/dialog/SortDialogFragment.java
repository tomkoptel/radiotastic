package com.udacity.study.jam.radiotastic.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.db.station.StationColumns;
import com.udacity.study.jam.radiotastic.ui.UiPref_;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@EFragment
public class SortDialogFragment extends DialogFragment {

    private static final String TAG = SortDialogFragment.class.getSimpleName();

    @Pref
    protected UiPref_ uiPref;

    public static void show(FragmentManager fragmentManager) {
        SortDialogFragment dialog = (SortDialogFragment) fragmentManager.findFragmentByTag(TAG);
        if (dialog == null) {
            dialog = SortDialogFragment_.builder().build();
            dialog.show(fragmentManager, TAG);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Map<String, String> data = new LinkedHashMap<>();
        data.put(StationColumns.NAME, getString(R.string.sort_by_title));
        data.put(StationColumns.BITRATE, getString(R.string.sort_by_bitrate));

        int index = new ArrayList<>(data.keySet()).indexOf(uiPref.sortOption().get());
        String[] items = new String[data.values().size()];
        data.values().toArray(items);

        builder.setTitle(R.string.sort_dialog_title);
        builder.setSingleChoiceItems(items, index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        uiPref.sortOption().put(StationColumns.NAME);
                        break;
                    case 1:
                        uiPref.sortOption().put(StationColumns.BITRATE);
                        break;
                }
                dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

}
