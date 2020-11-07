package edu.uga.cs.quizzer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class QuizActivity extends AppCompatActivity {

    private String chosenState;
    private int qnum;
    public static String questionNumber = "1.";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        chosenState = "Georgia";

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            qnum = 1;
            questionNumber = qnum + ".";
            Bundle arguments = new Bundle();
            arguments.putString(QuestionCardFragment.STATE, chosenState);
            QuestionCardFragment fragment = new QuestionCardFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.card_container, fragment)
                    .commit();
        }
    }
}