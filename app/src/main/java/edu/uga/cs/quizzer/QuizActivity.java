package edu.uga.cs.quizzer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    private String chosenState;
    private int qnum;
    private int[] stateIndices;
    public static String questionNumber = "1.";
    public static List<List<String>> statesInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        initStatesInfo();
        stateIndices = generateStateIndices();
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

    private void initStatesInfo() {
        statesInfo = new ArrayList<>();
        try {
            InputStream istream = getResources().openRawResource(R.raw.state_capitals);
            BufferedReader reader = new BufferedReader(new InputStreamReader(istream));
            StringBuilder output = new StringBuilder();
            String nl;
            while((nl = reader.readLine()) != null) {
                output.append(nl);
            }
            String fileStr = output.toString();
            Log.d("Tassle", fileStr);
            String[] tokens = fileStr.split("\n");
            for (String token : tokens) {
                statesInfo.add(parseNextCSVLine(token));
                Log.d("Turtle", "added new line");
            }

        } catch (Exception e) {
            Log.d("Turtle", "initStatesInfo threw exception");
            e.printStackTrace();
        }

        Log.d("Turtle", "scanned");
    }

    private List<String> parseNextCSVLine(String line) {
        List<String> lineValues = new ArrayList<String>();
        try {
            Scanner lineScanner = new Scanner(line);
            lineScanner.useDelimiter(",");
            while(lineScanner.hasNext()) {
                lineValues.add(lineScanner.next().trim());
            }
            lineScanner.close();
        } catch (Exception e) {
            Log.d("Turtle", "parseNextCSVLine threw exception");
            e.printStackTrace();
        }
        return lineValues;
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
}