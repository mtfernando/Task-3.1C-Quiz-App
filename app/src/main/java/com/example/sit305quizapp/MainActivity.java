package com.example.sit305quizapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name;
    Button startButton;
    String userName="";
    Integer finalScore=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.nameEditText);
        name.setText(userName);
        startButton = findViewById(R.id.startButton);

        //Start Quiz Activity on button click
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName =  name.getText().toString();
                name.setText(userName);
                Intent quizIntent = new Intent(MainActivity.this, QuizActivity.class);
                quizIntent.putExtra("name", userName);
                startActivityForResult(quizIntent, RESULT_FIRST_USER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(resultCode){

            //Result from QuizActivity
            case(RESULT_FIRST_USER):
            {
                finalScore = data.getIntExtra("score", -1);//Get quiz score from the quiz activity
                System.out.println("Final score is " + finalScore);

                //Start ScoreActivity
                Intent scoreIntent = new Intent(MainActivity.this, ScoreActivity.class);
                scoreIntent.putExtra("name", userName);
                scoreIntent.putExtra("score", finalScore);
                startActivityForResult(scoreIntent, 2);

            } break;

            //Result from ScoreActivity
            case(2):
            {
                userName = data.getStringExtra("name"); //Get User's name from score activity

            }break;
        }
    }

    public void showUserName(View view){
        userName =  name.getText().toString();
        Toast.makeText(getApplicationContext(), "Welcome " + userName, Toast.LENGTH_SHORT).show();
        System.out.println("Printing Now");
        System.out.println(userName);
    }
}