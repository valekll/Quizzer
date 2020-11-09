package edu.uga.cs.quizzer;


import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class QuestionCardAnswer extends Fragment implements View.OnClickListener {

    public QuestionCardAnswer() {
        //empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the layout for this particular fragment
        View rootView = inflater.inflate(R.layout.fragment_question_card, container, false);

        // Now we set the onClick for each of our views as the one implemented by this fragment

        rootView.findViewById(R.id.radioButton1).setOnClickListener(this);
        rootView.findViewById(R.id.radioButton2).setOnClickListener(this);
        rootView.findViewById(R.id.radioButton3).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButton1:
                Toast.makeText(getActivity(), "Button 1 selected", Toast.LENGTH_LONG). show();
            case R.id.radioButton2:
                Toast.makeText(getActivity(), "Button 2 selected", Toast.LENGTH_LONG). show();
            case R.id.radioButton3:
                Toast.makeText(getActivity(), "Button 3 selected", Toast.LENGTH_LONG). show();
        }

    }
}