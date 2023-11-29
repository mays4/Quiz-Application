package com.example.quizapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;



import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {
    Button btn_true , btn_false;
    private int currentQuestionIndex;
    ArrayList<Question> shuffledQuestions;
    int numberOfQuestions = 10;
    QuestionBank questionBank ;

    int correctAnswer = 0;
    int attempt = 0;
    int totalCorrect = 0;
    ProgressBar progressBar;
    FileManager fm ;
    Fragment f;

    Boolean changeSize = false;
    QuestionFragment qf;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentQuestionIndex", currentQuestionIndex);
        // Add other relevant data to be saved
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (savedInstanceState != null) {
//            currentQuestionIndex = savedInstanceState.getInt("currentQuestionIndex", 0);
//            // Restore other relevant data
//        }
        btn_true = findViewById(R.id.yes);
        btn_false = findViewById(R.id.no);
        progressBar = findViewById(R.id.progress_bar);
        btn_true.setOnClickListener(this);
        btn_false.setOnClickListener(this);
        shuffledQuestions=((MyApp)getApplication()).getQuestions();
        fm = ((MyApp)getApplication()).fileManager;
        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt("currentQuestionIndex", 0);
            // Restore other relevant data
        } else {
            // If there is no saved instance state, initialize currentQuestionIndex to 0
            currentQuestionIndex = 0;
        }
       questionBank= new QuestionBank(this, numberOfQuestions);
       shuffledQuestions = questionBank.getShuffleQuestions();
        startQuiz();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    private  void loadFragment(String text, int color,int numberOfQuestions) {

        qf = new QuestionFragment();
        qf.updateQuestionDetails(text, color, numberOfQuestions);
        Bundle bundle = new Bundle();
        bundle.putInt("numberOfQuestions", numberOfQuestions);
        qf.setArguments(bundle);
//       getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, qf).commit();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment f = fragmentManager.findFragmentById(R.id.frame_layout);
        if (f != null) {
            fragmentManager.beginTransaction().remove(f).commit();
        }
            fragmentManager.beginTransaction().add(R.id.frame_layout, qf).commit();


    }

    private void startQuiz() {
        if (qf == null) {
            qf = new QuestionFragment();
        }

        loadFragment(shuffledQuestions.get(currentQuestionIndex).getText(),
                shuffledQuestions.get(currentQuestionIndex).getColor(),
                updateNumberOfQuestions(numberOfQuestions));

//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.frame_layout, qf)
//                .commit();
    }

    private void checkAnswer(Boolean answer) {
        if (answer == shuffledQuestions.get(currentQuestionIndex).getAnswer()) {
            correctAnswer++;
            totalCorrect++;

            Toast.makeText(this, "correct answer", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (currentQuestionIndex < shuffledQuestions.size() - 1) {
            // Move to the next question
            currentQuestionIndex++;
            loadFragment(shuffledQuestions.get(currentQuestionIndex).getText(),
                    shuffledQuestions.get(currentQuestionIndex).getColor(),
                    updateNumberOfQuestions(numberOfQuestions));

            int progress = (currentQuestionIndex * 100) / shuffledQuestions.size();
            progressBar.setProgress(progress);
        } else {
            // Show the result or perform other actions
            String mesT = "Your Score is " + correctAnswer + " out of " + shuffledQuestions.size();
            showAlertDialog(mesT);
            currentQuestionIndex = 0;
            correctAnswer = 0;
            int progress = 0;
            progressBar.setProgress(progress);
        }
    }


    public void showAlertDialog(String mes) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.result))
                .setMessage(mes).setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        attempt++;

                        fm.writeQuizAnswerFile(MainActivity.this, correctAnswer, attempt, totalCorrect);
                    }
                })
                .setNegativeButton(getString(R.string.ignore), null)
                .create();
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public  void showAlertDialogToDeleteResult(String mes){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mes).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                attempt =0;
                totalCorrect= 0;
                fm.deleteAllResult(MainActivity.this);
            }
        }).setNegativeButton(getString(R.string.ignore),null);

        AlertDialog alertDialog  = builder.create();
        alertDialog.show();

    }


   private void showAlertDialogHowManyQuestions(String mes) {
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

    String[] items = {"5 questions", "10 Questions"};
    int checkedItem = 1;
    alertDialog.setSingleChoiceItems(items, checkedItem, (dialog, which) -> {
        switch (which) {
            case 0:
                Toast.makeText(MainActivity.this, "Clicked on 5 questions", Toast.LENGTH_LONG).show();
                changeSize = true;
                numberOfQuestions = 5;
                break;
            case 1:
                Toast.makeText(MainActivity.this, "Clicked on 10 questions", Toast.LENGTH_LONG).show();
                changeSize = true;
                numberOfQuestions = 10;
                break;
        }
    }).setPositiveButton(getString(R.string.ok), (dialog, which) -> {

        reloadFragment();
    }).setNegativeButton(getString(R.string.cancel), (dialog, which) -> {

    });

    AlertDialog alert = alertDialog.create();
    alert.setCanceledOnTouchOutside(false);
    alert.show();
}

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.average:

                AlertFragment alertFragment = AlertFragment.newInstance(fm.getResult(MainActivity.this));
                alertFragment.show(getSupportFragmentManager(), "AlertFragmentTag");
                return  true;
//                break;

            case R.id.number_question:
                String ms="how many question you want to show";
                showAlertDialogHowManyQuestions(ms);
                 return true;
            case R.id.reset:
                String mes="Are you sure you want to delete the result";
                showAlertDialogToDeleteResult(mes);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private int updateNumberOfQuestions(int numberOfQuestions) {
        return numberOfQuestions;
    }

    private void reloadFragment() {
        if (changeSize) {
            QuestionBank questionBank = new QuestionBank(this, numberOfQuestions);
            shuffledQuestions = questionBank.getShuffleQuestions();
            if (qf == null) {
                qf = new QuestionFragment();
            }
            qf.updateQuestionDetails(shuffledQuestions.get(currentQuestionIndex).getText(),
                    shuffledQuestions.get(currentQuestionIndex).getColor(),
                    updateNumberOfQuestions(numberOfQuestions));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, qf)
                    .commit();
        }
        changeSize = false;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes:
                checkAnswer(true);
//                if (shuffledQuestions != null && currentQuestionIndex < shuffledQuestions.size()) {
//                    checkAnswer(true);
//                }

                break;
            case R.id.no:
                checkAnswer(false);
//                if (shuffledQuestions != null && currentQuestionIndex < shuffledQuestions.size()) {
//                    checkAnswer(false);
//                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

}