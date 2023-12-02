package com.example.quizapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AlertFragment extends DialogFragment {

    static int answer;
    static  int questions;
    static  int attempt;



        public static AlertFragment newInstance(int totalAnswer, int totalQuestions, int totalAttempt){
        answer = totalAnswer;
        questions = totalQuestions;
        attempt = totalAttempt;
        return new AlertFragment();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext()).setMessage("Your Total correct answers are " + answer + " out of  " + questions + " questions  in " + attempt + " attempts." )
                .setPositiveButton(getString(R.string.ok),null).create();
    }
}
