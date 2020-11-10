package edu.uga.cs.quizzer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * An activity that displays a table of past scores on the quiz.
 */
public class ResultsActivity extends AppCompatActivity {

    protected static DatabaseHelper myDatabaseHelper;

    /**
     * Creates the activity based on a saved instance state.
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        //Get references to xml
        TextView resultsTable = findViewById(R.id.resultsTable);
        TextView resultsTable2 = findViewById(R.id.resultsTable2);
        //Add scores to table
        new GetScoresAsyncTask(this, resultsTable, resultsTable2).execute();
    }

    /**
     * Async Task to get scores from database.
     */
    private class GetScoresAsyncTask extends AsyncTask<Void, Void, Void> {

        private ArrayList<Result> myResults;
        private TextView resultsTable;
        private TextView resultsTable2;
        private ResultsActivity myResultsActivity;

        //Constructor to get some references
        public GetScoresAsyncTask(ResultsActivity myResultsActivity, TextView resultsTable, TextView resultsTable2) {
            this.myResultsActivity = myResultsActivity;
            myResults = new ArrayList<Result>();
            this.resultsTable = resultsTable;
            this.resultsTable2 = resultsTable2;
        }

        /**
         * The method to be conducted asynchronously
         * @return the DatabaseHelper object
         */
        @Override
        protected Void doInBackground(Void... voids) {
            //get a reference to the database helper class
            if(myDatabaseHelper == null) {
                myDatabaseHelper = DatabaseHelper.getInstance(myResultsActivity);
            }
            myResults = myDatabaseHelper.getResults();
            return null;
        }

        /**
         * Adds everything to the table.
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //build strings based on result class
            String resultsString = "";
            String resultsString2 = "";
            for(Result r : myResults) {
                resultsString += r.getDate() + "\n";
                resultsString2 += r.getScore() + "\n";
            }
            //set text
            resultsTable.setText(resultsString);
            resultsTable2.setText(resultsString2);
        }
    }
}