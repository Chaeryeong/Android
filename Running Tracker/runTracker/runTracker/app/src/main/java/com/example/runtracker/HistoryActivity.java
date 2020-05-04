package com.example.runtracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity  extends AppCompatActivity {
    /*
    HistoryActivity is to list all the records in list view and allows user to view the list in 2 sorted fashions.
     */

    DBHelper dbHelper;
    SQLiteDatabase db;
    SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        // get all records data
        Cursor c = db.query("records", new String[] { "_id", "year", "month", "day", "distance", "avgspeed" },
                null, null, null, null, null);

        String[] columns = new String[] {
                "year",
                "month",
                "day",
                "distance",
                "avgspeed" };

        int[] to = new int[] {
                R.id.value_year,
                R.id.value_month,
                R.id.value_day,
                R.id.value_distance,
                R.id.value_avgspeed };

        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.historylist_layout, // layout for each row
                c, //cursor
                columns,
                to,
                0);

        // display the retrieve bunch of data to the list view
        final ListView listView = (ListView) findViewById(R.id.recordsList);
        listView.setAdapter(dataAdapter);

    }

    // sort by best distance (distance descendant order)
    public void onSortByDistButtonClick(View v){
        //recordlistview sorted from best distance to worst distance

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        // get the data in an order of distance-descendant
        Cursor c = db.query("records", new String[] { "_id", "year", "month", "day", "distance", "avgspeed" },
                null, null, null, null, "distance desc");

        String[] columns = new String[] {
                "year",
                "month",
                "day",
                "distance",
                "avgspeed" };

        int[] to = new int[] {
                R.id.value_year,
                R.id.value_month,
                R.id.value_day,
                R.id.value_distance,
                R.id.value_avgspeed };

        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.historylist_layout, // layout for each row
                c, //cursor
                columns,
                to,
                0);

        final ListView listView = (ListView) findViewById(R.id.recordsList);
        listView.setAdapter(dataAdapter);

    }

    // sort by best speeds
    public void onSortBySpeedButtonClick(View v){
        //recordslistview sorted from best speeds to worst speeds

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        // get the data in an order of averageSpeed-descendant
        Cursor c = db.query("records", new String[] { "_id", "year", "month", "day", "distance", "avgspeed" },
                null, null, null, null, "avgspeed desc");

        String[] columns = new String[] {
                "year",
                "month",
                "day",
                "distance",
                "avgspeed" };

        int[] to = new int[] {
                R.id.value_year,
                R.id.value_month,
                R.id.value_day,
                R.id.value_distance,
                R.id.value_avgspeed };

        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.historylist_layout, // layout for each row
                c, //cursor
                columns,
                to,
                0);

        final ListView listView = (ListView) findViewById(R.id.recordsList);
        listView.setAdapter(dataAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("g53mdp", "HistoryActivity onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("g53mdp", "HistoryActivity onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("g53mdp", "HistoryActivity onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("g53mdp", "HistoryActivity onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("g53mdp", "HistoryActivity onStop");
    }
}
