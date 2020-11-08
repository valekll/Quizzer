package edu.uga.cs.quizzer;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

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

    /**
     * Constructor for DatabaseHelper class.
     */
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the tables in the SQL database.
     * @param sqLiteDatabase the database
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_STATES_TABLE = "CREATE TABLE " + TABLE_STATES +
                "(" + KEY_STATES_ID + " INT(2) NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                KEY_STATES_STATE +" VARCHAR(15) NOT NULL," +
                KEY_STATES_CAPITAL_CITY + " VARCHAR(20) NOT NULL," +
                KEY_STATES_SECOND_CITY + " VARCHAR(20) NOT NULL," +
                KEY_STATES_THIRD_CITY + " VARCHAR(20) NOT NULL," +
                KEY_STATES_STATEHOOD + " INT(4) NOT NULL," +
                KEY_STATES_CAPITAL_SINCE + " INT(4) NOT NULL," +
                KEY_STATES_SIZE_RANK + " INT(2) NOT NULL" +
                ")";
        String CREATE_RESULTS_TABLE = "CREATE TABLE " + TABLE_RESULTS +
                "(" + KEY_RESULTS_ID +" INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                KEY_RESULTS_DATE + " DATE NOT NULL DEFAULT SYSDATE," +
                KEY_RESULTS_SCORE + " INT(3) NOT NULL," +
                KEY_RESULTS_Q1 + " VARCHAR(15) NOT NULL," +
                KEY_RESULTS_Q1_R + " BOOLEAN NOT NULL," +
                KEY_RESULTS_Q2 + " VARCHAR(15) NOT NULL," +
                KEY_RESULTS_Q2_R + " BOOLEAN NOT NULL," +
                KEY_RESULTS_Q3 + " VARCHAR(15) NOT NULL," +
                KEY_RESULTS_Q3_R + " BOOLEAN NOT NULL," +
                KEY_RESULTS_Q4 + " VARCHAR(15) NOT NULL," +
                KEY_RESULTS_Q4_R + " BOOLEAN NOT NULL," +
                KEY_RESULTS_Q5 + " VARCHAR(15) NOT NULL," +
                KEY_RESULTS_Q5_R + " BOOLEAN NOT NULL," +
                KEY_RESULTS_Q6 + " VARCHAR(15) NOT NULL," +
                KEY_RESULTS_Q6_R + " BOOLEAN NOT NULL" +
                ")";
        sqLiteDatabase.execSQL(CREATE_STATES_TABLE);
        sqLiteDatabase.execSQL(CREATE_RESULTS_TABLE);
    }

    /**
     * For upgrading the database to a newer version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //version mismatch: drop old tables and readd current ones
        if (i != i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STATES);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
            onCreate(sqLiteDatabase);
        }
    }

    /**
     * Add a state to the database
     * @param stateInfo the info of the state being input to the database
     */
    public void addState(List<String> ... stateInfo) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        for(List<String> inf : stateInfo) {
            try {
                ContentValues vals = new ContentValues();
                for(int i = 0; i < inf.size(); i++) {
                    switch (i) {
                        case 0: vals.put(KEY_STATES_STATE, inf.get(i));
                            break;
                        case 1: vals.put(KEY_STATES_CAPITAL_CITY, inf.get(i));
                            break;
                        case 2: vals.put(KEY_STATES_SECOND_CITY, inf.get(i));
                            break;
                        case 3: vals.put(KEY_STATES_THIRD_CITY, inf.get(i));
                            break;
                        case 4: vals.put(KEY_STATES_STATEHOOD, inf.get(i));
                            break;
                        case 5: vals.put(KEY_STATES_CAPITAL_SINCE, inf.get(i));
                            break;
                        case 6: vals.put(KEY_STATES_SIZE_RANK, inf.get(i));
                            break;
                    }
                }
                db.insertOrThrow(TABLE_STATES, null, vals);
            } catch (Exception e) {
                Log.d("Turtle", "Exception found adding state to database.");
                e.printStackTrace();
            }
        }
        db.endTransaction();
    }

    /**
     * Allows user to access the same version of the database helper throughout multiple invocations
     * from different locations.
     * @param context the application context
     * @return the DatabaseHelper instance being used
     */
    public static synchronized DatabaseHelper getInstance(Context context) {
        if(myInstance == null) {
            myInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return myInstance;
    }
}
