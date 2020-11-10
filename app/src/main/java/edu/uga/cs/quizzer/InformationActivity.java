package edu.uga.cs.quizzer;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InformationActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_information);

            TextView textView = findViewById(R.id.textView);

            textView.setText(
                    "How to play the game: \n" +
                    "To start Quizzer, please select on the start button \n"
            );
        }
}
