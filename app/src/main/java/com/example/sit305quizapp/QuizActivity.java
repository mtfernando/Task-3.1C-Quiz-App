package com.example.sit305quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{

    Integer score, currentQuestion = 1;
    ProgressBar progressBar;
    TextView welcomeText, progressText, questionTitle, questionDescription;
    Button ans1,ans2,ans3,submit, userSelection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //Initializing views with local vars
        progressBar = findViewById(R.id.progressBar);
        welcomeText = findViewById(R.id.WelcomeText);
        progressText = findViewById(R.id.PorgressText);
        questionTitle = findViewById(R.id.questionTitle);
        questionDescription = findViewById(R.id.questionDesc);
        ans1 = findViewById(R.id.ans1);
        ans2 = findViewById(R.id.ans2);
        ans3 = findViewById(R.id.ans3);
        submit = findViewById(R.id.submitButton);

        setQuestion();
    }

    public void setQuestion(){
        //Set name in welcome string
        String welcomeTextString = "Welcome " + getIntent().getStringExtra("name") + "!";
        welcomeText.setText(welcomeTextString);

        //Set progress bar and progress text
        progressBar.setProgress(currentQuestion);
        progressText.setText(currentQuestion.toString() + "/5");
    }

    @Override
    public void onClick(View v) {
        //Clear selection of buttons if any are already selected
        ans1.setBackgroundColor(getResources().getColor(R.color.ans_btn));
        ans2.setBackgroundColor(getResources().getColor(R.color.ans_btn));
        ans3.setBackgroundColor(getResources().getColor(R.color.ans_btn));

        //Set new background color to highlight selected answer.
        switch (v.getId()) {
            case R.id.ans1:
                System.out.println("Answer 1");
                ans1.setBackgroundColor(getResources().getColor(R.color.selected_answer_btn));
                break;

            case R.id.ans2:
                System.out.println("Answer 2");
                ans2.setBackgroundColor(getResources().getColor(R.color.selected_answer_btn));
                break;
            case R.id.ans3:
                System.out.println("Answer 3");
                ans3.setBackgroundColor(getResources().getColor(R.color.selected_answer_btn));
                break;
        }
    }
}