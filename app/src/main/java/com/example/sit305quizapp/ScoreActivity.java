package com.example.sit305quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    String userName;
    TextView congratulationsText, yourScoreText, finalScoreText;
    Button newQuizButton, finishButton;
    Integer score;
    boolean newQuiz; //Checks whether the user wants to start a new quiz or to finish. True if new quiz, False if finish.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        //Assign views to local vars
        congratulationsText = findViewById(R.id.congratulationsText);
        yourScoreText = findViewById(R.id.yourScoreText);
        finalScoreText = findViewById(R.id.finalScoreText);

        newQuizButton = findViewById(R.id.newQuizButton);
        finishButton = findViewById(R.id.finishButton);

        //Get data from intent
        userName = getIntent().getStringExtra("name");
        score = getIntent().getIntExtra("score", -1);

        //Set text views based on data from intent
        congratulationsText.setText("Congratulations " + userName + "!");
        finalScoreText.setText(score.toString() + "/5");
    }

    public void startNewQuiz(View view){
        newQuiz = true; //Boolean variable will tell the MainActivity whether to continue with a new quiz

        Intent intent  = new Intent(ScoreActivity.this, MainActivity.class);
        intent.putExtra("newQuiz", newQuiz);
        intent.putExtra("name", userName);
        setResult(2, intent);
        finish();
    }

    public void finishQuiz(View view){
        newQuiz = false;

        Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
        intent.putExtra("newQuiz", newQuiz);
        setResult(2, intent);
        finish();
    }
}