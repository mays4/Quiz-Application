package com.example.quizapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QuestionFragment extends Fragment {
    static String questionText;
    static int questionColor;
   static int numberOfQuestions;

    public QuestionFragment() {

    }
    public static QuestionFragment newInstance(String text,int color,int numberOfQuestions){
        QuestionFragment questionF = new   QuestionFragment();
        questionText = text;
        questionColor = color;
        return questionF ;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question,container,false);
        TextView questionView = view.findViewById(R.id.questionView);
        questionView.setText(questionText);
        view.setBackgroundResource(questionColor);
        Bundle bundle = getArguments();
        if (bundle != null) {
            numberOfQuestions = bundle.getInt("numberOfQuestions", 10);
        }

        return view;
    }


}
