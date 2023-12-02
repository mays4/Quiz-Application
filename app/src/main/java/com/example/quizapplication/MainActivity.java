package com.example.quizapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {
    Button  btn_true , btn_false;
    private int currentQuestionIndex;
    QuestionBank questionBank ;
    ArrayList<Question> questionsArray;
    int correctAnswer = 0;
    int attempt = 0;
    int totalCorrect = 0;


    ProgressBar progressBar;
    FileManager fm ;
    FragmentManager fragmentManager;
    Fragment f;
    Boolean changeSize = false;
    QuestionFragment ff;
    QuestionFragment qf;

    int totalQuestions;
    int overallQuestions;

    int attemptBN;
    int answerBN;
    int questionsBN;
    int totalAttempt;

    int updateNum;
    int numberOfQuestions=10;
    String[] res;
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentQuestionIndex", currentQuestionIndex);
        outState.putInt("correctAnswer", correctAnswer);
        outState.putInt("attempt", attempt);
        outState.putInt("totalCorrect", totalCorrect);
        outState.putInt("totalQuestions", totalQuestions);
        outState.putInt("num", updateNum);
        outState.putInt("numberOfQuestions", numberOfQuestions);
        outState.putParcelableArrayList("questionsArray", questionsArray);
        outState.putInt("overall",overallQuestions);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt("currentQuestionIndex", 0);
            correctAnswer = savedInstanceState.getInt("correctAnswer", 0);
            attempt = savedInstanceState.getInt("attempt", 0);
            totalCorrect = savedInstanceState.getInt("totalCorrect", 0);
            numberOfQuestions = savedInstanceState.getInt("numberOfQuestions", 0);
            questionsArray = savedInstanceState.getParcelableArrayList("questionsArray");
            updateNum = savedInstanceState.getInt("num");
            totalQuestions=savedInstanceState.getInt("totalQuestions");
            overallQuestions=savedInstanceState.getInt("overall");
        }
        btn_true = findViewById(R.id.yes);
        btn_false = findViewById(R.id.no);
        progressBar = findViewById(R.id.progress_bar);
        btn_true.setOnClickListener(this);
        btn_false.setOnClickListener(this);
        fm = ((MyApp)getApplication()).fileManager;
        res= fm.getResult(this).split("-");
        answerBN=Integer.parseInt(res[0].trim());
        questionsBN = Integer.parseInt(res[1].trim());
        attemptBN = Integer.parseInt(res[2].trim());




        fragmentManager = getSupportFragmentManager();
        f = fragmentManager.findFragmentById(R.id.frame_layout);
        if(f!= null){
            questionsArray=((MyApp)getApplication()).getQuestions();

        }else{
            questionBank = new QuestionBank(this,numberOfQuestions);
            questionsArray = questionBank.getShuffleQuestions();
            ((MyApp)getApplication()).setQuestions(questionsArray);
        }

      int num = ((MyApp)getApplication()).getNumberOfQuestions();
        updateNum=(num ==0)?questionsArray.size():num;

        startQuiz();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    private void startQuiz() {
        loadFragment(questionsArray.get(currentQuestionIndex).getText(),
                questionsArray.get(currentQuestionIndex).getColor(),
                updateNumberOfQuestions(numberOfQuestions));

        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, qf)
                .commit();
    }
    private void loadFragment(String text, int color, int numberOfQuestions) {
        qf = new QuestionFragment();
        ff = QuestionFragment.newInstance(text,color,numberOfQuestions);
        Bundle bundle = new Bundle();
        bundle.putInt("numberOfQuestions",numberOfQuestions);
        qf.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        f = fragmentManager.findFragmentById(R.id.frame_layout);

        if (f != null) {

            fragmentManager.beginTransaction().remove(f).commit();
        }
        fragmentManager.beginTransaction().replace(R.id.frame_layout, qf).commit();


    }



    private void checkAnswer(Boolean answer) {
        if (answer == questionsArray.get(currentQuestionIndex).getAnswer()) {
            correctAnswer++;
            totalCorrect++;

            Toast.makeText(this,R.string.correct, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.wrong, Toast.LENGTH_SHORT).show();
        }


        if (currentQuestionIndex < questionsArray.size() - 1) {
            currentQuestionIndex++;
            loadFragment(questionsArray.get(currentQuestionIndex).getText(),
                    questionsArray.get(currentQuestionIndex).getColor(),
                    updateNumberOfQuestions(numberOfQuestions));

            int progress = (currentQuestionIndex * 100) / numberOfQuestions;
            progressBar.setProgress(progress);
        } else {
            String mesT = "Your Score is " + correctAnswer + " out of " + numberOfQuestions;
            showAlertDialog(mesT);
            currentQuestionIndex = 0;
          correctAnswer = 0;
            int progress = 0;
            progressBar.setProgress(progress);
            Collections.shuffle(questionsArray);
            ((MyApp)getApplication()).setQuestions(questionsArray);
            loadFragment(questionsArray.get(currentQuestionIndex).getText(),
                    questionsArray.get(currentQuestionIndex).getColor(),
                    updateNumberOfQuestions(numberOfQuestions));


        }
    }


    private int updateNumberOfQuestions(int numberOfQuestions) {
        return numberOfQuestions;
    }

    private void reloadFragment() {
        if (changeSize) {
            questionBank = new QuestionBank(this,numberOfQuestions);
            questionsArray = questionBank.getShuffleQuestions();
            ((MyApp)getApplication()).setQuestions(questionsArray);

            ff = QuestionFragment.newInstance(questionsArray.get(currentQuestionIndex).getText(),
                    questionsArray.get(currentQuestionIndex).getColor(),
                    updateNumberOfQuestions(numberOfQuestions));
        }
        changeSize = false;
    }

    // menu
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes:

                checkAnswer(true);
                break;
            case R.id.no:
                checkAnswer(false);

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
    public void showAlertDialog(String mes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.result))
                .setMessage(mes)
                .setPositiveButton(getString(R.string.save), (dialog, which) -> {
                    res = fm.getResult(this).split("-");
                    answerBN = Integer.parseInt(res[0].trim());
                    questionsBN = Integer.parseInt(res[1].trim());
                    attemptBN = Integer.parseInt(res[2].trim());


                   int  currentAttemptCorrect = answerBN + totalCorrect;



                    totalAttempt = attemptBN + 1;


                    overallQuestions = questionsBN + numberOfQuestions;

                   // Write the updated values to the file
                    fm.writeQuizAnswerFile(MainActivity.this, currentAttemptCorrect, overallQuestions, totalAttempt);
                    totalCorrect = 0;

                })
                .setNegativeButton(getString(R.string.ignore), null)
                .create();
// Reset totalQuestions to 0
        totalQuestions = 0;

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void showAlertDialogToDeleteResult(String mes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mes).setPositiveButton(getString(R.string.ok), (dialog, which) -> {
            // Reset relevant variables
            attempt = 0;
            totalCorrect = 0;
            fm.deleteAllResult(MainActivity.this);



        }).setNegativeButton(getString(R.string.ignore), null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }





    private void showAlertDialogHowManyQuestions() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        String[] items = {"5 questions", "10 Questions"};
        int checkedItem = 1;
        alertDialog.setSingleChoiceItems(items, checkedItem, (dialog, which) -> {
            switch (which) {
                case 0:
                    Toast.makeText(MainActivity.this, "5 questions Selected", Toast.LENGTH_LONG).show();
                    changeSize = true;
                    numberOfQuestions = 5;
                    break;
                case 1:
                    Toast.makeText(MainActivity.this, "10 questions Selected", Toast.LENGTH_LONG).show();
                    changeSize = true;
                    numberOfQuestions = 10;
                    break;
            }
        }).setPositiveButton(getString(R.string.ok), (dialog, which) -> reloadFragment()).setNegativeButton(getString(R.string.cancel), (dialog, which) -> {

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
//                Log.d("resul",fm.getResult(MainActivity.this)+"");
                res= fm.getResult(this).split("-");
                Log.d("resul",fm.getResult(MainActivity.this)+"");
                int ans=Integer.parseInt(res[0].trim());
                int q= Integer.parseInt(res[1].trim());
                int att = Integer.parseInt(res[2].trim());



                AlertFragment alertFragment = AlertFragment.newInstance(ans,q,att );
//                AlertFragment alertFragment = AlertFragment.newInstance(fm.getResult(MainActivity.this));
                alertFragment.show(getSupportFragmentManager(), "AlertFragmentTag");
                startQuiz();
                return  true;

            case R.id.number_question:

                showAlertDialogHowManyQuestions();
                return true;
            case R.id.reset:
                String mes="Are you sure you want to delete the result";
                showAlertDialogToDeleteResult(mes);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}