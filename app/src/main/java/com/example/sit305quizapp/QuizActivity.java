package com.example.sit305quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class QuizActivity extends AppCompatActivity {

    Integer score, currentQuestion = 1;
    ProgressBar progressBar;
    TextView welcomeText, progressText, quesitonTitle, questionDescription;
    Button ans1,ans2,ans3,submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //Initializing views with local vars
        progressBar = findViewById(R.id.progressBar);
        welcomeText = findViewById(R.id.WelcomeText);
        progressText = findViewById(R.id.PorgressText);
        quesitonTitle = findViewById(R.id.questionTitle);
        questionDescription = findViewById(R.id.questionDesc);
        ans1 = findViewById(R.id.ans1);
        ans2 = findViewById(R.id.ans2);
        ans3 = findViewById(R.id.ans3);
        submit = findViewById(R.id.submitButton);

        //Set name in welcome string
        String welcomeTextString = "Welcome " + getIntent().getStringExtra("name" + "!");
        welcomeText.setText(welcomeTextString);

        //Set progress bar and progress text
        progressBar.setProgress(currentQuestion);
        progressText.setText(currentQuestion.toString() + "/5");
    }
}