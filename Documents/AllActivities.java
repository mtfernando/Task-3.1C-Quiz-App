package com.example.sit305quizapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name;
    Button startButton;
    String userName="";
    Integer finalScore=0;
    boolean newQuiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign views to local vars
        name = findViewById(R.id.nameEditText);
        name.setText(userName);
        startButton = findViewById(R.id.startButton);

        //Start Quiz Activity on button click
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName =  name.getText().toString();

                //Error handling for not entering name
                if(userName.matches("")){
                    Toast.makeText(getApplicationContext(), "Please enter your name!", Toast.LENGTH_SHORT).show();
                }
                else{
                    System.out.println("Username is " + userName);
                    name.setText(userName);
                    Intent quizIntent = new Intent(MainActivity.this, QuizActivity.class);
                    quizIntent.putExtra("name", userName);
                    startActivityForResult(quizIntent, RESULT_FIRST_USER);

                }
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
                newQuiz = data.getBooleanExtra("newQuiz", true);

                //End quiz if the user selects to end it
                if(!newQuiz){
                    finish();
                }
            }break;
        }
    }

//    public void showUserName(View view){
//        userName =  name.getText().toString();
//        Toast.makeText(getApplicationContext(), "Welcome " + userName, Toast.LENGTH_SHORT).show();
//        System.out.println("Printing Now");
//        System.out.println(userName);
//    }
}

package com.example.sit305quizapp;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.w3c.dom.Text;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{

    Integer score = 0, currentQuestion = 1;
    ProgressBar progressBar;
    TextView welcomeText, progressText, questionTitle, questionDescription;
    Button ans1,ans2,ans3,submit, userSelection;
    String[][] questions = new String[5][];
    String correctAnswer;
    Button[] answerButtons = new Button[3];
    Boolean answerSelected = false;
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
        ans1.setTag(1);
        answerButtons[0] = ans1;

        ans2 = findViewById(R.id.ans2);
        ans2.setTag(2);
        answerButtons[1] = ans2;

        ans3 = findViewById(R.id.ans3);
        ans3.setTag(3);
        answerButtons[2] = ans3;

        submit = findViewById(R.id.submitButton);
        submit.setTag(0);



        questions[0] = getResources().getStringArray(R.array.question1);
        questions[1] = getResources().getStringArray(R.array.question2);
        questions[2] = getResources().getStringArray(R.array.question3);
        questions[3] = getResources().getStringArray(R.array.question4);
        questions[4] = getResources().getStringArray(R.array.question5);

        setQuestion();
    }

    public void setQuestion(){
        //Set name in welcome string
        String welcomeTextString = "Welcome " + getIntent().getStringExtra("name") + "!";
        welcomeText.setText(welcomeTextString);

        //Setting Question title, description, and answers
        questionTitle.setText(questions[currentQuestion-1][0]);
        questionDescription.setText(questions[currentQuestion-1][1]);
        ans1.setText(questions[currentQuestion-1][2]);
        ans2.setText(questions[currentQuestion-1][3]);
        ans3.setText(questions[currentQuestion-1][4]);

        //Save correct answer
        correctAnswer = questions[currentQuestion-1][5];

        //Set progress bar and progress text
        progressBar.setProgress(currentQuestion);
        progressText.setText(currentQuestion.toString() + "/5");

        //Clear selection of buttons if any are already selected
        ans1.setBackgroundColor(getResources().getColor(R.color.ans_btn));
        ans2.setBackgroundColor(getResources().getColor(R.color.ans_btn));
        ans3.setBackgroundColor(getResources().getColor(R.color.ans_btn));
    }

    @Override
    public void onClick(View v) {
        //Clear selection of buttons if any are already selected
        ans1.setBackgroundColor(getResources().getColor(R.color.ans_btn));
        ans2.setBackgroundColor(getResources().getColor(R.color.ans_btn));
        ans3.setBackgroundColor(getResources().getColor(R.color.ans_btn));

        //Set new background color to highlight selected answer.
        //Save selected answer button view in userSelection.
        switch (v.getId()) {
            case R.id.ans1:
                System.out.println("Answer 1 is selected");
                ans1.setBackgroundColor(getResources().getColor(R.color.selected_answer_btn));
                userSelection = ans1;
                break;

            case R.id.ans2:
                System.out.println("Answer 2 is selected");
                ans2.setBackgroundColor(getResources().getColor(R.color.selected_answer_btn));
                userSelection = ans2;
                break;
            case R.id.ans3:
                System.out.println("Answer 3 is selected");
                ans3.setBackgroundColor(getResources().getColor(R.color.selected_answer_btn));
                userSelection = ans3;
                break;
        }
    }

    public void submitClick(View v){

        //Check state of button and proceed

        //When SUBMIT is clicked
        if (Integer.parseInt(v.getTag().toString())==0) {
            //Calculating score
            Integer selectedAnswer = -1;

            //try-catch for when an answer is not selected.
            try {
                selectedAnswer = Integer.parseInt(userSelection.getTag().toString());
                answerSelected = true;
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Please select an answer!", Toast.LENGTH_SHORT).show();
            }

            //Proceed only if an answer has been selected
            if (answerSelected) {
                System.out.println("Selected answer is " + selectedAnswer);
                selectedAnswer = Integer.parseInt(userSelection.getTag().toString());

                //Check selected answer against correct answer defined in strings.xml
                if (selectedAnswer == Integer.parseInt(questions[currentQuestion - 1][5])) {
                    System.out.println("Correct Answer!");
                    userSelection.setBackgroundColor(getResources().getColor(R.color.correct_answer_btn));
                    score = score + 1;
                } else {
                    System.out.println("Incorrect answer! Try again.");
                    userSelection.setBackgroundColor(getResources().getColor(R.color.incorrect_answer_btn));
                    answerButtons[Integer.parseInt(questions[currentQuestion - 1][5]) - 1].setBackgroundColor(getResources().getColor(R.color.correct_answer_btn));
                }

                //Make buttons unclickable once answer is submitted
                ans1.setClickable(false);
                ans2.setClickable(false);
                ans3.setClickable(false);

                //Change button text and tag
                submit.setText("NEXT");
                submit.setTag(1);
            }
        }

        //When NEXT is clicked
        else{
            //Clear previous selection
            userSelection = null;
            answerSelected=false;

            System.out.println("Next question appears now...");
            currentQuestion = currentQuestion + 1;

            //Only 5 questions will be displayed.
            if(currentQuestion<6){
                submit.setText("SUBMIT");
                submit.setTag(0);
                setQuestion();

                //Make buttons clickable
                ans1.setClickable(true);
                ans2.setClickable(true);
                ans3.setClickable(true);
            }

            else{
                submit.setTag(3);

                //End activity and send results to MainActivity.
                System.out.println("ScoreActivity starts now.");
                System.out.println("Score is " + score);

                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                intent.putExtra("score", score);
                setResult(RESULT_FIRST_USER, intent);

                finish();
            }
        }
    }
}

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