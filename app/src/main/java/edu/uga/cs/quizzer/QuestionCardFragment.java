package edu.uga.cs.quizzer;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * A fragment that displays a card with a quiz question.
 */
public class QuestionCardFragment extends Fragment {

    // the fragment initialization parameters
    protected static final String STATE = "state";
    protected static final String QNUM = "questionNumber";
    public static final String STATE_INDEX = "stateIndex";

    //declare vars
    private State chosenState;
    private int chosenStateIndex;
    private int questionNumber;
    private String selectedAnswer;
    private TextView questionNumText = null;
    private TextView questionText = null;
    private RadioButton rb1 = null;
    private RadioButton rb2 = null;
    private RadioButton rb3 = null;

    // Required empty public constructor
    public QuestionCardFragment() {
    }

    /**
     * Creates the fragment based on a saved instance state.
     * @param savedInstanceState the saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get the args provided
        if (getArguments() != null) {
            questionNumber = getArguments().getInt(QNUM);
            chosenStateIndex = getArguments().getInt(STATE_INDEX);
        }
    }

    /**
     * Creates the view for the fragment
     * @param inflater the inflater for the xml
     * @param container the view's container
     * @param savedInstanceState the saved instance state
     * @return the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_question_card, container,
                false);
        //Get the state info from the database
        try {
            chosenState = new StateQueryAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Set the card text
        if(chosenState != null) {
            //find the buttons in xml
            questionNumText = (TextView)rootView.findViewById(R.id.questionNumberTextView);
            questionText = (TextView)rootView.findViewById(R.id.quizQuestionTextView);
            rb1 = (RadioButton)rootView.findViewById(R.id.radioButton1);
            rb2 = (RadioButton)rootView.findViewById(R.id.radioButton2);
            rb3 = (RadioButton)rootView.findViewById(R.id.radioButton3);
            //set values
            questionNumText.setText(questionNumber + ".");
            questionText.setText("What is the capital of " + chosenState.getName() + "?");
            //initialize answer files
            writeInitialAnswerToFile();
            //randomize answer vals
            Random rand = new Random();
            int choice1 = rand.nextInt(3);
            int choice2 = rand.nextInt(2);
            ArrayList<String> cities = new ArrayList<String>();
            cities.add(chosenState.getCapital());
            cities.add(chosenState.getCity1());
            cities.add(chosenState.getCity2());
            //setup radio buttons to their choices and make them save user input
            rb1.setText(cities.remove(choice1));
            rb1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedAnswer = (String) rb1.getText();
                    writeAnswerToFile();
                }
            });
            rb2.setText(cities.remove(choice2));
            rb2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedAnswer = (String) rb2.getText();
                    writeAnswerToFile();
                }
            });
            rb3.setText(cities.remove(0));
            rb3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedAnswer = (String) rb3.getText();
                    writeAnswerToFile();
                }
            });
        }
        return rootView;
    }

    /**
     * Writes the current selected answer to a question file.
     */
    private void writeAnswerToFile() {
        String ans = "";
        //check for correctness
        if(selectedAnswer != null && chosenState != null &&
                selectedAnswer.equalsIgnoreCase(chosenState.getCapital())) {
            Log.d("Titanium", "state: " + chosenState.getName() + " selected: " + selectedAnswer + " actual: " + chosenState.getCapital());
            Log.d("Titanium", "correct");
            ans = "1.0";
        }
        //incorrect
        else {
            Log.d("Titanium", "state: " + chosenState.getName() + " selected: " + selectedAnswer + " actual: " + chosenState.getCapital());
            ans = "0.0";
        }
        //get file
        File path = getContext().getFilesDir();
        Log.d("Titanium", "path: |" + path.getPath());
        File file = new File(path, "q" + questionNumber + ".ans");
        //write to file
        try {
            FileOutputStream str = new FileOutputStream(file);
            str.write(ans.getBytes());
            str.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Zeroes out the question in case it goes unanswered in file.
     */
    private void writeInitialAnswerToFile() {
        //get file path
        File path = getContext().getFilesDir();
        Log.d("Titanium", "path: |" + path.getPath());
        File file = new File(path, "q" + questionNumber + ".ans");
        //write to file
        try {
            FileOutputStream str = new FileOutputStream(file);
            str.write("0.0".getBytes());
            str.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Async Task to make a database query for a state's info.
     */
    private class StateQueryAsyncTask extends AsyncTask<Void, Void, State> {
        /**
         * The method to be conducted asynchronously
         * @return the State object
         */
        @Override
        protected State doInBackground(Void... voids) {
            return ScreenSlidePagerActivity.myDatabaseHelper.getState(chosenStateIndex);
        }

    }
}