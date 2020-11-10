package edu.uga.cs.quizzer;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionCardFragment extends Fragment {

    // the fragment initialization parameters
    protected static final String STATE = "state";
    protected static final String QNUM = "questionNumber";

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
        if (QuizActivity.chosenStates != null)
            chosenState = QuizActivity.chosenStates.get(questionNumber);
        //Set the card text
        if(chosenState != null) {

            for(int i = 0; i < ((ConstraintLayout)rootView.findViewById(R.id.questionCardLayout)).getChildCount(); i++) {
                if(((ConstraintLayout)rootView.findViewById(R.id.questionCardLayout)).getChildAt(i).getId() == R.id.questionNumberTextView) {
                    questionNumText = (TextView)((ConstraintLayout)rootView.findViewById(R.id.questionCardLayout)).getChildAt(i);
                }
                else if(((ConstraintLayout)rootView.findViewById(R.id.questionCardLayout)).getChildAt(i).getId() == R.id.quizQuestionTextView) {
                    questionText = (TextView)((ConstraintLayout)rootView.findViewById(R.id.questionCardLayout)).getChildAt(i);
                }
                else if(((ConstraintLayout)rootView.findViewById(R.id.questionCardLayout)).getChildAt(i).getId() == R.id.radioButton1) {
                    rb1 = (RadioButton) ((ConstraintLayout)rootView.findViewById(R.id.questionCardLayout)).getChildAt(i);
                }
                else if(((ConstraintLayout)rootView.findViewById(R.id.questionCardLayout)).getChildAt(i).getId() == R.id.radioButton2) {
                    rb2 = (RadioButton) ((ConstraintLayout)rootView.findViewById(R.id.questionCardLayout)).getChildAt(i);
                }
                else if(((ConstraintLayout)rootView.findViewById(R.id.questionCardLayout)).getChildAt(i).getId() == R.id.radioButton3) {
                    rb3 = (RadioButton) ((ConstraintLayout)rootView.findViewById(R.id.questionCardLayout)).getChildAt(i);
                }
            }

            //set values
            questionNumText.setText(questionNumber);
            questionText.setText("What is the capital of " + chosenState.getName());
            Random rand = new Random();
            int choice1 = rand.nextInt(3);
            int choice2 = rand.nextInt(2);
            ArrayList<String> cities = new ArrayList<String>();
            cities.add(chosenState.getCapital());
            cities.add(chosenState.getCity1());
            cities.add(chosenState.getCity2());
            rb1.setText(cities.remove(choice1));
            rb2.setText(cities.remove(choice2));
            rb3.setText(cities.remove(0));

        }

        return rootView;
    }
}