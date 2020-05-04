package com.example.runtracker;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context, "psyck2DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("g54mdp", "onDBCreate");

        db.execSQL("CREATE TABLE logs (" +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                "latitude double NOT NULL,"+
                "longitude double NOT NULL,"+
                "time long NOT NULL);");

        db.execSQL("CREATE TABLE records ("+
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                "year INTEGER NOT NULL,"+
                "month INTEGER NOT NULL,"+
                "day INTEGER NOT NULL,"+
                "distance double NOT NULL,"+
                "avgspeed double NOT NULL,"+
                "feeling VARCHAR(128),"+
                "weather VARCHAR(128),"+
                "comment VARCHAR(128),"+
                "duration long NOT NULL);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS logs");
        db.execSQL("DROP TABLE IF EXISTS records");
        onCreate(db);
    }
}