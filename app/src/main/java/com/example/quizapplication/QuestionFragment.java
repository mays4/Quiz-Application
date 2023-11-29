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

    private  String questionText;
    private int questionColor;
    private int numberOfQuestions;


    public QuestionFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//         super.onCreateView(inflater, container, savedInstanceState);
         View view = inflater.inflate(R.layout.fragment_question,container,false);
        TextView questionView = view.findViewById(R.id.questionView);
        questionView.setText(questionText);
        view.setBackgroundColor(getResources().getColor(questionColor, null));
        Bundle bundle = getArguments();
        if (bundle != null) {
            numberOfQuestions = bundle.getInt("numberOfQuestions", 10); // Default to 10 if not found
        }

        return view;
    }
    public void updateQuestionDetails(String text, int color,int numberOfQuestions) {
        this.questionText = text;
        this.questionColor = color;
    }

}
