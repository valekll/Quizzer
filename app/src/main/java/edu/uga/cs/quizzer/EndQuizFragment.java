package edu.uga.cs.quizzer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple fragment to complete the quiz
 */
public class EndQuizFragment extends Fragment {

    // Required empty public constructor
    public EndQuizFragment() {
    }

    /**
     * Creates the Fragment based on savedInstanceState
     * @param savedInstanceState the state the fragment should be created in
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Creates the view for the fragment
     * @param inflater a view inflater for the xml
     * @param container the container in the view
     * @param savedInstanceState the state in which the fragment is being created.
     * @return the view for display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_end_quiz, container, false);
        // Create button to submit quiz
        Button submit = (Button)rootView.findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent endQuiz = new Intent(rootView.getContext(), ResultsPageActivity.class);
                startActivity(endQuiz);
            }
        });
        return rootView;
    }
}