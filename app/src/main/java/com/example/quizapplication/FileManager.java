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

    void writeQuizAnswerFile(Context context, int totalCorrect, int totalQuestions, int totalAttempt) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);

            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
//
//                // Read the existing values
//                int existingTotalCorrect = 0;
//                int existingTotalQuestions = 0;
//                int existingTotalAttempt = 0;
//
//                if (line != null) {
//                    String[] parts = line.split("-");
//                    if (parts.length == 3) {
//                        existingTotalCorrect = Integer.parseInt(parts[0]);
//                        existingTotalQuestions = Integer.parseInt(parts[1]);
//                        existingTotalAttempt = Integer.parseInt(parts[2]);
//                    }
//                }
//
//                // Update the values
//                existingTotalCorrect += totalCorrect;
//                existingTotalQuestions += totalQuestions;
//                existingTotalAttempt += totalAttempt;

                // Write the updated values back
                FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                String total = totalCorrect+ "-" + totalQuestions + "-" + totalAttempt + "\n";
                fos.write(total.getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist, initialize with zeros
            try {
                FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                fos.write("0-0-0\n".getBytes());
                fos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    void deleteAllResult(Context context) {
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write("0-0-0\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    String getResult(Context context) {
        String lastLine = "0-0-0\n";
        try (FileInputStream fis = context.openFileInput(fileName);
             InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(inputStreamReader)) {

            String line = reader.readLine();
            while (line != null) {
                lastLine = line;
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastLine;
    }
}
