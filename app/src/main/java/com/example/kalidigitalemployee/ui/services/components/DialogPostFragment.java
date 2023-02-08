package com.example.kalidigitalemployee.ui.services.components;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class DialogPostFragment extends DialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        String title = getArguments().getString("title");
        String body = getArguments().getString("body");

        return builder
                .setTitle(title)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(body)
                .setPositiveButton("Закрыть", null)
                .create();
    }
}