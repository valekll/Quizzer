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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionCardFragment extends Fragment {

    // the fragment initialization parameters
    protected static final String STATE = "state";

    private String chosenState;
    private TextView questionNumText = null;
    private TextView questionText = null;
    private RadioButton rb1 = null;
    private RadioButton rb2 = null;
    private RadioButton rb3 = null;

    public QuestionCardFragment() {
        // Required empty public constructor
    }

    /**
     * This method is used to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param chosenState the chosen state.
     * @return A new instance of fragment QuestionCardFragment.
     */
    public static QuestionCardFragment newInstance(String chosenState) {
        QuestionCardFragment fragment = new QuestionCardFragment();
        Bundle args = new Bundle();
        args.putString(STATE, chosenState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            chosenState = getArguments().getString(STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_question_card, container,
                false);
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
            questionNumText.setText(QuizActivity.questionNumber);
            questionText.setText("What is the capital of " + chosenState);
        }

        return rootView;
    }
}