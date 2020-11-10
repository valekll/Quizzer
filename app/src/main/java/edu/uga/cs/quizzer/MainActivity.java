package edu.uga.cs.quizzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button resultsButton;
    private Button informationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.startQuizButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quizIntent = new Intent(view.getContext(), ScreenSlidePagerActivity.class);
                MainActivity.this.startActivity(quizIntent);
            }
        });

        resultsButton = (Button)findViewById(R.id.resultsButton);
        resultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultsIntent = new Intent(view.getContext(), ResultsActivity.class);
                MainActivity.this.startActivity(resultsIntent);
            }
        });

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
