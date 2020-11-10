package edu.uga.cs.quizzer;

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

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionCardFragment extends Fragment {

    // the fragment initialization parameters
    protected static final String STATE = "state";
    protected static final String QNUM = "questionNumber";

    private State chosenStateIndex;
    private State chosenState;
    private int questionNumber;
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

            questionNumText = (TextView)rootView.findViewById(R.id.questionNumberTextView);
            questionText = (TextView)rootView.findViewById(R.id.quizQuestionTextView);
            rb1 = (RadioButton)rootView.findViewById(R.id.radioButton1);
            rb2 = (RadioButton)rootView.findViewById(R.id.radioButton2);
            rb3 = (RadioButton)rootView.findViewById(R.id.radioButton3);
            //set values
            questionNumText.setText(questionNumber + ".");
            questionText.setText("What is the capital of " + chosenState.getName());
            Random rand = new Random();
            int choice1 = rand.nextInt(3);
            int choice2 = rand.nextInt(2);
            ArrayList<String> cities = new ArrayList<String>();
            cities.add(chosenState.getCapital());
            cities.add(chosenState.getCity1());
            cities.add(chosenState.getCity2());
            String ans1 = cities.remove(choice1);
            rb1.setText(ans1);
            rb2.setText(cities.remove(choice2));
            rb3.setText(cities.remove(0));

        }

        return rootView;
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
            return QuizActivity.myDatabaseHelper.getState(questionNumber);
        }

    }
}