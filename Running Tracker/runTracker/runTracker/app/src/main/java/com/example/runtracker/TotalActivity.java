package com.example.runtracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TotalActivity extends AppCompatActivity {
    /*
    TotalActivity is to compute and display the total distance the user run in the month and the year.
     */

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        // get intent and retreive month,year data passed from MonthYearActivity
        Intent intent = getIntent();
        String year = intent.getStringExtra("year");
        String month = intent.getStringExtra("month");
        String[] this_month = {year, month};
        String[] this_year = {year};

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        // access records table and compute accumulate data of the month using the month and year key
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM records WHERE year = ? and month = ?", this_month);
        double month_distance = 0;
        while (cursor.moveToNext()) {
            String dist = cursor.getString(4) ; //distance
            double temp = Double.parseDouble(dist);
            month_distance += temp;
        }

        // access records table and compute accumulate data of the year using the year key
        Cursor cursor_2;
        cursor_2 = db.rawQuery("SELECT * FROM records WHERE year = ?", this_year);
        double year_distance = 0;
        while (cursor_2.moveToNext()) {
            String dist = cursor_2.getString(4) ; //distance
            double temp = Double.parseDouble(dist);
            year_distance += temp;
        }

        // display accumulated data
        TextView total_month = (TextView)findViewById(R.id.monthTotal);
        TextView total_year = (TextView)findViewById(R.id.yearTotal);
        total_month.setText(month_distance+"");
        total_year.setText(year_distance+"");

    }

    public void onBackButtonClick(View v){
        //intent to MainActivity
         // back to the main activity when the button pressed
        Intent intent_2 = new Intent(TotalActivity.this, MainActivity.class);
        startActivity(intent_2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("g53mdp", "TotalActivity onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("g53mdp", "TotalActivity onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("g53mdp", "TotalActivity onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("g53mdp", "TotalActivity onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("g53mdp", "TotalActivity onStop");
    }

}
