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

public class ResultsPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);

        int score = (int)getScore();
        String scoreString = score + "%";
        TextView scoreText = (TextView)findViewById(R.id.score);
        scoreText.setText(scoreString);
        new AddScoreAsyncTask(score).execute();

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
        double count = 0.0;
        for(int i = 1; i < 7; i++) {
            try {
                File myFile = new File("/data/user/0/edu.uga.cs.quizzer/files/q" + i + ".ans");
                Log.d("Titanium", "file found " + i);
                Scanner myScanner = new Scanner(myFile);
                Log.d("Titanium", "scanner created " + i);
                if(myScanner.hasNextLine()) {
                    Log.d("Titanium", "scanner has next line " + i);
                    String nextLine = myScanner.nextLine();
                    Log.d("Titanium", "nl:" + nextLine);
                    double answer = Double.parseDouble(nextLine);
                    Log.d("Titanium", Double.toString(answer));
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