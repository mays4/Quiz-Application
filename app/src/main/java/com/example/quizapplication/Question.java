package com.example.quizapplication;

import android.content.Context;

import java.io.Serializable;

public class Question implements Serializable {
    String text;
    Boolean answer;
    int color;

    public Question(Context context, String text, boolean answer, int color) {
        this.text = text;
        this.answer = answer;
        this.color = color;
    }


    public String getText() {
        return text;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public int getColor() {
        return color;
    }



}
