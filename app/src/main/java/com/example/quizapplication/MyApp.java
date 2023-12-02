package com.example.quizapplication;

import android.app.Application;

import java.util.ArrayList;


public class MyApp extends Application {
    ArrayList<Question> questions;
    int numberOfQuestions;


    FileManager fileManager = new FileManager();

    public ArrayList<Question> getQuestions() {
        if(questions == null){
           questions = new ArrayList<>(0);
        }
        return questions;
    }



    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
