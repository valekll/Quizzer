package edu.uga.cs.quizzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The homescreen activity for the application.
 */
public class MainActivity extends AppCompatActivity {

    //declare vars
    private Button startButton;
    private Button resultsButton;
    private Button informationButton;

    /**
     * Creates the activity based on a saved instance state
     * @param savedInstanceState the state in which to create the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get reference to start button and make it start quiz
        startButton = (Button)findViewById(R.id.startQuizButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quizIntent = new Intent(view.getContext(), ScreenSlidePagerActivity.class);
                MainActivity.this.startActivity(quizIntent);
            }
        });

        //Get reference to results button and make it lead to results table page
        resultsButton = (Button)findViewById(R.id.resultsButton);
        resultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultsIntent = new Intent(view.getContext(), ResultsActivity.class);
                MainActivity.this.startActivity(resultsIntent);
            }
        });

        //Get reference to the how to play button and make it lead to respective page
        informationButton = (Button)findViewById(R.id.informationButton);
        informationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent informationIntent = new Intent(view.getContext(), InformationActivity.class);
                MainActivity.this.startActivity(informationIntent);
            }
        });
    }
}
