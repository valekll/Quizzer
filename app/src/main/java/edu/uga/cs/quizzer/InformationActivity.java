package edu.uga.cs.quizzer;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InformationActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_information);

            TextView textView = findViewById(R.id.textView1);

            textView.setText(
                    "How to play the game: \n\n" +
                        "To start, please select on the start button \n\n" +
                        "The app will now prompt the user to answer \n\n" +
                        "Swipe left when the question has been answered (total of six questions) \n\n" +
                        "After all six questions have been answered, your results will be shown\n\n\n\n" +
                    "Additional Information: \n\n" +
                            "To view past result scores, please select on the results button\n\n"

            );
        }
}
