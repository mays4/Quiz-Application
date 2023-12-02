package com.example.quizapplication;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;



public class Question implements Parcelable {
    String text;
    Boolean answer;
    int color;

    public Question(Context context, String text, boolean answer, int color) {
        this.text = text;
        this.answer = answer;
        this.color = color;
    }


    protected Question(Parcel in) {
        text = in.readString();
        byte tmpAnswer = in.readByte();
        answer = tmpAnswer == 0 ? null : tmpAnswer == 1;
        color = in.readInt();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getText() {
        return text;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public int getColor() {
        return color;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeByte((byte) (answer == null ? 0 : answer ? 1 : 2));
        dest.writeInt(color);
    }
}
