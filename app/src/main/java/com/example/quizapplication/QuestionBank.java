package com.example.quizapplication;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionBank {

    private final ArrayList<Question> questions;
//    private final ArrayList<Question> colors;


    public QuestionBank(Context context, int numberOfQuestions) {
        this.questions = new ArrayList<>(0);
        initializeQuestions(context);
        setNumberOfQuestions(numberOfQuestions);
    }



    private void initializeQuestions(Context context) {

//        if (questions == null) {
//
//
        questions.add(new Question(context,context.getString(R.string.question_1), false, R.color.Coral));
        questions.add(new Question(context,context.getString(R.string.question_2),true, R.color.LightGreen));
        questions.add(new Question(context,context.getString(R.string.question_3), true, R.color.Red));
        questions.add(new Question(context,context.getString(R.string.question_4), true, R.color.Coral));
        questions.add(new Question(context,context.getString(R.string.question_5),false, R.color.DarkSalmon));
        questions.add(new Question(context,context.getString(R.string.question_6), false, R.color.Chocolate));
        questions.add(new Question(context,context.getString(R.string.question_7), true, R.color.DarkOrange));
        questions.add(new Question(context,context.getString(R.string.question_8),false, R.color.Purple));
        questions.add(new Question(context,context.getString(R.string.question_9), true, R.color.LightPink));
        questions.add(new Question(context,context.getString(R.string.question_10), true, R.color.LightBlue));

//        }
    }
  //  private void initializeColors() {

//        if (questions == null) {

//        colors.add(new Question(String.valueOf(R.string.question_1), true, 10));

//        }
  //  }
    private void shuffleQuestions() {
        Collections.shuffle(questions);
    }

//    private void shuffleColors() {
//        Collections.shuffle(colors);
//    }



    public void setNumberOfQuestions(int numberOfQuestions) {
        if (numberOfQuestions < questions.size()) {
            Collections.shuffle(questions);
            questions.subList(numberOfQuestions, questions.size()).clear();
        }
    }

    public ArrayList<Question> getShuffleQuestions() {
        shuffleQuestions();
        return new ArrayList<>(questions);
    }

}

