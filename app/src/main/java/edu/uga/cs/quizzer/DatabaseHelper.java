package edu.uga.cs.quizzer;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quizzerAppDatabase";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_STATES_TABLE = "CREATE TABLE states" +
                "(" + "id INT(2) NOT NULL PRIMARY KEY," +
                "state VARCHAR(15) NOT NULL," +
                "capitalCity VARCHAR(20) NOT NULL," +
                "secondCity VARCHAR(20) NOT NULL," +
                "thirdCity VARCHAR(20) NOT NULL," +
                "statehood INT(4) NOT NULL," +
                "capitalSince INT(4) NOT NULL," +
                "sizeRank INT(2) NOT NULL" +
                ")";
        String CREATE_RESULTS_TABLE = "CREATE TABLE results" +
                "(" +
                ")";
        sqLiteDatabase.execSQL(CREATE_STATES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
