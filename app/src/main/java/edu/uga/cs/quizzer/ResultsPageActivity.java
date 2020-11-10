package edu.uga.cs.quizzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.Scanner;

/**
 * A simple activity used to display the results of the current quiz
 */
public class ResultsPageActivity extends AppCompatActivity {

    /**
     * Creates the activity based on a saved instance state
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);
        //get the quiz score
        int score = (int)getScore();
        String scoreString = score + "%";
        //get reference to the textview and set text to appropriate score
        TextView scoreText = (TextView)findViewById(R.id.score);
        scoreText.setText(scoreString);
        //add score to database
        new AddScoreAsyncTask(score).execute();
        //get reference to a button and make it return to main screen
        Button homeButton = (Button)findViewById(R.id.returnButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHome = new Intent(view.getContext(), MainActivity.class);
                startActivity(goHome);
            }
        });
    }

    /**
     * Gets score from files.
     * @return the score as an int
     */
    private double getScore() {
        String fileText;
        //add total correct answers
        double count = 0.0;
        for(int i = 1; i < 7; i++) {
            try {
                //read from score files
                File myFile = new File("/data/user/0/edu.uga.cs.quizzer/files/q" + i + ".ans");
                Scanner myScanner = new Scanner(myFile);
                if(myScanner.hasNextLine()) {
                    //make score readable
                    String nextLine = myScanner.nextLine();
                    double answer = Double.parseDouble(nextLine);
                    count += answer;
                }
                myScanner.close();
            } catch (Exception e) {
                Log.d("Titanium", "error");
                e.printStackTrace();
            }
        }
        return count / 6.0 * 100.0;
    }

    /**
     * Async Task to add score to database.
     */
    private class AddScoreAsyncTask extends AsyncTask<Void, Void, Void> {

        private int score;

        /**
         * Constructor
         * @param score score to be added
         */
        public AddScoreAsyncTask(int score) {
            this.score = score;
        }

        /**
         * The method to be conducted asynchronously
         * @return the DatabaseHelper object
         */
        @Override
        protected Void doInBackground(Void... voids) {
            ScreenSlidePagerActivity.myDatabaseHelper.addResult(score);
            return null;
        }
    }
}