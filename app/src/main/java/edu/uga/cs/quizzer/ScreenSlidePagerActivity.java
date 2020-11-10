package edu.uga.cs.quizzer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.io.InputStream;
import java.util.List;
import java.util.Random;

public class ScreenSlidePagerActivity extends FragmentActivity {

    //declare some vars
    private int questionNumber;
    protected static List<State> chosenStates;
    protected static InputStream CSVinputstream;
    protected static DatabaseHelper myDatabaseHelper;

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 7;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            CSVinputstream = getResources().openRawResource(R.raw.state_capitals);
            new InitDatabaseAsyncTask(this).execute();

            // Instantiate a ViewPager and a PagerAdapter.
            mPager = (ViewPager) findViewById(R.id.pager);
            pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(pagerAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 7 fragments, in sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public int[] stateIndices;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
            stateIndices = generateStateIndices();
        }

        @Override
        public Fragment getItem(int position) {
            questionNumber = position + 1;
            int stateIndex = stateIndices[position];
            Bundle arguments = new Bundle();
            arguments.putInt(QuestionCardFragment.QNUM, questionNumber);
            arguments.putInt(QuestionCardFragment.STATE_INDEX, stateIndex);
            Log.d("Turtle", "args put in bundle");

            QuestionCardFragment fragment = new QuestionCardFragment();
            fragment.setArguments(arguments);
            //getSupportFragmentManager().beginTransaction().add(R.id.card_container, fragment).commit();
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }



    /**
     * Generates six unique random states by number to quiz on.
     * @return the array of the unique state indices
     */
    private int[] generateStateIndices() {
        int[] vals = new int[6];
        Random rand = new Random();
        for(int i = 0; i < 6; i++) {
            boolean valFound = false;
            while(!valFound) {
                int x = rand.nextInt(50);
                if (!containsVal(vals, x)) {
                    vals[i] = x + 1;
                    valFound = true;
                }
            }
        }
        return vals;
    }

    /**
     * Checks if a int value is in an array
     * @param vals the array
     * @param v the value
     * @return whether it's in the array already
     */
    private boolean containsVal(int[] vals, int v) {
        for(int i = 0; i < vals.length; i++)
            if(vals[i] == v) return true;
        return false;
    }

    /**
     * Async Task to initialize database.
     */
    private class InitDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {

        private ScreenSlidePagerActivity activity;

        /**
         * Pass through for a reference to current quiz activity.
         * @param activity the reference
         */
        public InitDatabaseAsyncTask(ScreenSlidePagerActivity activity) {
            this.activity = activity;
        }

        /**
         * The method to be conducted asynchronously
         * @return the DatabaseHelper object
         */
        @Override
        protected Void doInBackground(Void... voids) {
            myDatabaseHelper = DatabaseHelper.getInstance(activity);
            return null;
        }
    }

}
