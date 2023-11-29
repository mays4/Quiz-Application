package com.example.quizapplication;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileManager {
String fileName = "quiz_answer.txt";
    FileOutputStream fos;

 void writeQuizAnswerFile(Context context, int correctAnswer, int attempt, int totalCorrect){
     try {
         fos = context.openFileOutput(fileName,Context.MODE_APPEND);
         String total = "You correct answers are " + totalCorrect + " in " + attempt + " attempts " + "\n";

         fos.write(total.getBytes());
         fos.close();

     }catch (FileNotFoundException e){
         e.printStackTrace();

     } catch (IOException e) {
         throw new RuntimeException(e);
     }finally {
         try {

             if (fos != null) {
                 fos.close();
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
 }

    void deleteAllResult(Context context){
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write("".getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    String getResult(Context context) {
        String lastLine = "You correct answers are " + 0 + " in " + 0 + " attempts " + "\n";
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();

                while (line != null) {
                    lastLine = line;
                    line = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lastLine;
    }

}
