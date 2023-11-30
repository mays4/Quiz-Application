package com.example.quizapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AlertFragment extends DialogFragment {

    static String msg ;

    public static AlertFragment newInstance(String m){
        msg = m;

        return new AlertFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext()).setMessage(msg )
                .setPositiveButton(getString(R.string.ok),null).create();
    }
}
