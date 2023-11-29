package com.example.quizapplication;

import android.app.Application;

import java.util.ArrayList;

public class MyApp extends Application {
    ArrayList<Question> questions;
    FileManager fileManager = new FileManager();

    public ArrayList<Question> getQuestions() {
        if(questions == null){
           questions = new ArrayList<>(0);
        }
        return questions;
    }

}
