package edu.uga.cs.quizzer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class QuizActivity extends AppCompatActivity {

    private int questionNumber;
    protected static int[] stateIndices;
    protected static List<State> chosenStates;
    protected static InputStream CSVinputstream;
    protected static DatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            CSVinputstream = getResources().openRawResource(R.raw.state_capitals);

            new InitDatabaseAsyncTask(this).execute();
            int[] stateIndices = generateStateIndices();

            questionNumber = 1;
            Bundle arguments = new Bundle();
            arguments.putInt(QuestionCardFragment.QNUM, questionNumber);
            Log.d("Turtle", "args put in bundle");

            QuestionCardFragment fragment = new QuestionCardFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.card_container, fragment).commit();

        }
    }

    /**
     * Generates six unique random states by number to quiz on.
     * @return the array of the unique state indices
     */
    private int[] generateStateIndices() {
        int[] vals = new int[6];
        Random rand = new Random();
        for(int i = 0; i < 6; i++) {
            boolean valFound = false;
            while(!valFound) {
                int x = rand.nextInt(50);
                if (!containsVal(vals, x)) {
                    vals[i] = x + 1;
                    valFound = true;
                }
            }
        }
        return vals;
    }

    /**
     * Checks if a int value is in an array
     * @param vals the array
     * @param v the value
     * @return whether it's in the array already
     */
    private boolean containsVal(int[] vals, int v) {
        for(int i = 0; i < vals.length; i++)
            if(vals[i] == v) return true;
        return false;
    }

    /**
     * Async Task to initialize database.
     */
    private class InitDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {

        private QuizActivity quizActivity;

        /**
         * Pass through for a reference to current quiz activity.
         * @param quizActivity the reference
         */
        public InitDatabaseAsyncTask(QuizActivity quizActivity) {
            this.quizActivity = quizActivity;
        }

        /**
         * The method to be conducted asynchronously
         * @return the DatabaseHelper object
         */
        @Override
        protected Void doInBackground(Void... voids) {
            myDatabaseHelper = DatabaseHelper.getInstance(quizActivity);
            return null;
        }
    }


}