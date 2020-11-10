package edu.uga.cs.quizzer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    protected static DatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextView resultsTable = findViewById(R.id.resultsTable);
        TextView resultsTable2 = findViewById(R.id.resultsTable2);
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
            if(myDatabaseHelper == null) {
                myDatabaseHelper = DatabaseHelper.getInstance(myResultsActivity);
            }
            myResults = myDatabaseHelper.getResults();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String resultsString = "";
            String resultsString2 = "";
            for(Result r : myResults) {
                resultsString += r.getDate() + "\n";
                resultsString2 += r.getScore() + "\n";
            }
            resultsTable.setText(resultsString);
            resultsTable2.setText(resultsString2);
        }
    }
}