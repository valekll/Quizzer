package edu.uga.cs.quizzer;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionCardFragment extends Fragment {

    // the fragment initialization parameters
    protected static final String STATE = "state";
    protected static final String QNUM = "questionNumber";
    public static final String STATE_INDEX = "stateIndex";

    private State chosenState;
    private int chosenStateIndex;
    private int questionNumber;
    private String selectedAnswer;
    private TextView questionNumText = null;
    private TextView questionText = null;
    private RadioButton rb1 = null;
    private RadioButton rb2 = null;
    private RadioButton rb3 = null;

    public QuestionCardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            questionNumber = getArguments().getInt(QNUM);
            chosenStateIndex = getArguments().getInt(STATE_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_question_card, container,
                false);
        try {
            chosenState = new StateQueryAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("Turtle", "Chosen State needed here.");
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void writeAnswerToFile() {
        String ans = "";
        if(selectedAnswer != null && chosenState != null &&
                selectedAnswer.equalsIgnoreCase(chosenState.getCapital())) {
            Log.d("Titanium", "state: " + chosenState.getName() + " selected: " + selectedAnswer + " actual: " + chosenState.getCapital());
            Log.d("Titanium", "correct");
            ans = "1.0";
        }
        else {
            Log.d("Titanium", "state: " + chosenState.getName() + " selected: " + selectedAnswer + " actual: " + chosenState.getCapital());
            ans = "0.0";
        }
        File path = getContext().getFilesDir();
        Log.d("Titanium", "path: |" + path.getPath());
        File file = new File(path, "q" + questionNumber + ".ans");

        try {
            FileOutputStream str = new FileOutputStream(file);
            str.write(ans.getBytes());
            str.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeInitialAnswerToFile() {
        File path = getContext().getFilesDir();
        Log.d("Titanium", "path: |" + path.getPath());
        File file = new File(path, "q" + questionNumber + ".ans");

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