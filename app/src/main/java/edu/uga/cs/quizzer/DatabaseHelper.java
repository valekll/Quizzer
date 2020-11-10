package edu.uga.cs.quizzer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class DatabaseHelper extends SQLiteOpenHelper {

    //Only instance to prevent multiple copies occurring
    private static DatabaseHelper myInstance;

    //Database Info
    private static final String DATABASE_NAME = "quizzerAppDatabase";
    private static final int DATABASE_VERSION = 1;

    //Table Names
    private static final String TABLE_STATES = "states";
    private static final String TABLE_RESULTS = "results";

    //States table cols
    private static final String KEY_STATES_ID = "id";
    private static final String KEY_STATES_STATE = "state";
    private static final String KEY_STATES_CAPITAL_CITY = "capitalCity";
    private static final String KEY_STATES_SECOND_CITY = "secondCity";
    private static final String KEY_STATES_THIRD_CITY = "thirdCity";
    private static final String KEY_STATES_STATEHOOD = "statehood";
    private static final String KEY_STATES_CAPITAL_SINCE = "capitalSince";
    private static final String KEY_STATES_SIZE_RANK = "sizeRank";

    //Results table cols
    private static final String KEY_RESULTS_ID = "id";
    private static final String KEY_RESULTS_DATE = "date";
    private static final String KEY_RESULTS_SCORE = "score";
    /*
    private static final String KEY_RESULTS_Q1 = "question1";
    private static final String KEY_RESULTS_Q1_R = "question1results";
    private static final String KEY_RESULTS_Q2 = "question2";
    private static final String KEY_RESULTS_Q2_R = "question2results";
    private static final String KEY_RESULTS_Q3 = "question3";
    private static final String KEY_RESULTS_Q3_R = "question3results";
    private static final String KEY_RESULTS_Q4 = "question4";
    private static final String KEY_RESULTS_Q4_R = "question4results";
    private static final String KEY_RESULTS_Q5 = "question5";
    private static final String KEY_RESULTS_Q5_R = "question5results";
    private static final String KEY_RESULTS_Q6 = "question6";
    private static final String KEY_RESULTS_Q6_R = "question6results";
    */
    public static List<List<String>> statesInfo;

    /**
     * Constructor for DatabaseHelper class.
     */
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //init the ArrayList to be used
        initStatesInfo();
        //add all the info of the states to the database
        for(List<String> line : statesInfo) {
            addState(line);
        }
    }

    /**
     * Creates the tables in the SQL database.
     * @param sqLiteDatabase the database
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("Turtle", "Database is being created.");
        //Create command strings
        String CREATE_STATES_TABLE = "CREATE TABLE " + TABLE_STATES +
                "(" + KEY_STATES_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                KEY_STATES_STATE +" TEXT NOT NULL," +
                KEY_STATES_CAPITAL_CITY + " TEXT NOT NULL," +
                KEY_STATES_SECOND_CITY + " TEXT NOT NULL," +
                KEY_STATES_THIRD_CITY + " TEXT NOT NULL," +
                KEY_STATES_STATEHOOD + " INTEGER NOT NULL," +
                KEY_STATES_CAPITAL_SINCE + " INTEGER NOT NULL," +
                KEY_STATES_SIZE_RANK + " INTEGER NOT NULL" +
                ")";
        String CREATE_RESULTS_TABLE = "CREATE TABLE " + TABLE_RESULTS +
                "(" + KEY_RESULTS_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                KEY_RESULTS_DATE + " TEXT NOT NULL," +
                KEY_RESULTS_SCORE + " INTEGER NOT NULL" +
                /*
                KEY_RESULTS_Q1 + " TEXT NOT NULL," +
                KEY_RESULTS_Q1_R + " INTEGER NOT NULL," +
                KEY_RESULTS_Q2 + " TEXT NOT NULL," +
                KEY_RESULTS_Q2_R + " INTEGER NOT NULL," +
                KEY_RESULTS_Q3 + " TEXT NOT NULL," +
                KEY_RESULTS_Q3_R + " INTEGER NOT NULL," +
                KEY_RESULTS_Q4 + " TEXT NOT NULL," +
                KEY_RESULTS_Q4_R + " INTEGER NOT NULL," +
                KEY_RESULTS_Q5 + " TEXT NOT NULL," +
                KEY_RESULTS_Q5_R + " INTEGER NOT NULL," +
                KEY_RESULTS_Q6 + " TEXT NOT NULL," +
                KEY_RESULTS_Q6_R + " INTEGER NOT NULL" + */
                ")";
        //exec command strings to make tables
        sqLiteDatabase.execSQL(CREATE_STATES_TABLE);
        Log.d("Turtle", "States table created.");
        sqLiteDatabase.execSQL(CREATE_RESULTS_TABLE);
        Log.d("Turtle", "Results table created.");
        Log.d("Turtle", "Database created");

    }

    /**
     * For upgrading the database to a newer version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //version mismatch: drop old tables and read current ones
        if (i != i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STATES);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
            Log.d("Turtle", "Old Tables Removed.");
            onCreate(sqLiteDatabase);
        }
    }

    /**
     * Add a state to the database
     * @param stateInfo the info of the state being input to the database
     */
    public void addState(List<String> stateInfo) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            //dictionary for the values
            ContentValues vals = new ContentValues();
            for(int i = 0; i < stateInfo.size(); i++) {
                //pair each key and value
                switch (i) {
                    case 0: vals.put(KEY_STATES_STATE, stateInfo.get(i));
                    break;
                    case 1: vals.put(KEY_STATES_CAPITAL_CITY, stateInfo.get(i));
                    break;
                    case 2: vals.put(KEY_STATES_SECOND_CITY, stateInfo.get(i));
                    break;
                    case 3: vals.put(KEY_STATES_THIRD_CITY, stateInfo.get(i));
                    break;
                    case 4: vals.put(KEY_STATES_STATEHOOD, stateInfo.get(i));
                    break;
                    case 5: vals.put(KEY_STATES_CAPITAL_SINCE, stateInfo.get(i));
                    break;
                    case 6: vals.put(KEY_STATES_SIZE_RANK, stateInfo.get(i));
                    break;
                }
            }
            Log.d("Transformer", vals.toString());
            //insert into the database table
            long rowNum = db.insertOrThrow(TABLE_STATES, null, vals);
            db.setTransactionSuccessful();
            Log.d("Transformer", "Row Num: " + rowNum);
        } catch (Exception e) {
            Log.d("Turtle", "Exception found adding state to database.");
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Add a score to the database
     * @param score the score
     */
    public void addResult(int score) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            //dictionary for the values
            ContentValues vals = new ContentValues();
            vals.put(KEY_RESULTS_SCORE, score);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());
            vals.put(KEY_RESULTS_DATE, date);
            Log.d("Turkey", vals.toString());
            //insert into the database table
            long rowNum = db.insertOrThrow(TABLE_RESULTS, null, vals);
            db.setTransactionSuccessful();
            Log.d("Turkey", "Row Num: " + rowNum);
        } catch (Exception e) {
            Log.d("Turkey", "Exception found adding score to database.");
            Log.e("Turkey", "e.printStackTrace()", e);
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Gets a state based on it's ID in the table.
     * @param id the state's id in the database
     * @return
     */
    public State getState(int id) {
        String GET_STATE_QUERY = "SELECT * FROM " + TABLE_STATES + " WHERE " + KEY_STATES_ID + " = " + id;
        SQLiteDatabase myDatabase = getReadableDatabase();
        Cursor myCursor = myDatabase.rawQuery(GET_STATE_QUERY, null);
        Log.d("Transformer", "Cursor count: " + myCursor.getCount());
        String[] cols = {KEY_STATES_STATE, KEY_STATES_CAPITAL_CITY, KEY_STATES_SECOND_CITY, KEY_STATES_THIRD_CITY};
        //Cursor myCursor = myDatabase.query(TABLE_STATES, cols, KEY_RESULTS_ID + " = " + id,
        //        null, null, null, null);
        //List<State> myStates = new ArrayList<State>();
        State myState = null;
        if(myCursor.moveToFirst()) {
            do {
                String s = myCursor.getString(myCursor.getColumnIndex(KEY_STATES_STATE));
                myState = new State(
                        myCursor.getString(myCursor.getColumnIndex(KEY_STATES_STATE)),
                        myCursor.getString(myCursor.getColumnIndex(KEY_STATES_CAPITAL_CITY)),
                        myCursor.getString(myCursor.getColumnIndex(KEY_STATES_SECOND_CITY)),
                        myCursor.getString(myCursor.getColumnIndex(KEY_STATES_THIRD_CITY))
                );
                //myStates.add(myState);
            } while (myCursor.moveToNext());
        }
        myCursor.close();
        Log.d("Transformer", "State: \n" + myState);
        return myState;
    }

    /**
     * Gets all results in the table.
     * @return a list of the results
     */
    public ArrayList<Result> getResults() {
        String GET_RESULTS_QUERY = "SELECT * FROM " + TABLE_RESULTS;
        SQLiteDatabase myDatabase = getReadableDatabase();
        Cursor myCursor = myDatabase.rawQuery(GET_RESULTS_QUERY, null);
        Log.d("Turkey", "Cursor count: " + myCursor.getCount());
        String[] cols = {KEY_RESULTS_DATE, KEY_RESULTS_ID, KEY_RESULTS_SCORE};
        ArrayList<Result> myResults = new ArrayList<Result>();
        Result myResult = new Result();
        if(myCursor.moveToFirst()) {
            do {
                myResult = new Result(
                        myCursor.getString(myCursor.getColumnIndex(KEY_RESULTS_DATE)),
                        myCursor.getInt(myCursor.getColumnIndex(KEY_RESULTS_ID)),
                        myCursor.getInt(myCursor.getColumnIndex(KEY_RESULTS_SCORE))
                );
                myResults.add(myResult);
            } while (myCursor.moveToNext());
        }
        myCursor.close();
        return myResults;
    }

    /**
     * Allows user to access the same version of the database helper throughout multiple invocations
     * from different locations.
     * @param context the application context
     * @return the DatabaseHelper instance being used
     */
    public static synchronized DatabaseHelper getInstance(Context context) {
        //check if already initialized
        if(myInstance == null) {
            myInstance = new DatabaseHelper(context.getApplicationContext());
        }
        Log.d("Turtle", "Database helper is setup.");
        return myInstance;
    }

    /**
     * Initializes a List of List of Strings from the CSV file containing state info.
     */
    private void initStatesInfo() {
        statesInfo = new ArrayList<>();
        try {
            //Get CSV as InputStream
            InputStream istream = ScreenSlidePagerActivity.CSVinputstream;
            BufferedReader reader = new BufferedReader(new InputStreamReader(istream));
            //Build a string from the InputString
            StringBuilder output = new StringBuilder();
            String nl;
            while((nl = reader.readLine()) != null) {
                output.append(nl + "\n");
                Log.d("Truck", nl);
            }
            //String built - attach to String object
            String fileStr = output.toString();
            Log.d("Tassle", fileStr);
            //Tokenize string by lines
            String[] tokens = fileStr.split(",|\\r?\\n");
            Log.d("Turtle", "tokenized: " + Integer.toString(tokens.length));
            for(int i = 1; i < 51; i++) {
                int j = i * 7;
                List<String> listToAdd = new ArrayList<String>();
                for(int k = 0; k < 7; k++) {
                    listToAdd.add(tokens[k + j].trim());
                }
                statesInfo.add(listToAdd);
            }
            Log.d("Turtle", "Done adding tokens to Lists.");
        } catch (Exception e) {
            Log.d("Turtle", "initStatesInfo threw exception");
            e.printStackTrace();
        }
    }

}
