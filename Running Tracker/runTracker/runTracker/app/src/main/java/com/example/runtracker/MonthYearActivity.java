package com.example.runtracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MonthYearActivity extends AppCompatActivity {
    /*
    MonthYearActivity is to retrieve the month&year input from the user to see the accumulated distance the user ran for the month and the year.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthyear);
    }

    // navigate to the activity to view your total distance record of the month and year
    public void onAccumulateButtonClick(View v){
        //intent to TotalActivity

        // retrieve the month&year user typed
        EditText input_month = (EditText)findViewById(R.id.month);
        EditText input_year = (EditText)findViewById(R.id.year);
        String month= input_month.getText().toString();
        String year = input_year.getText().toString();

        // pass the month&year data to the AccumulateActivity so that it can accumulate the records on group by the month and year
        Intent intent= new Intent(MonthYearActivity.this, TotalActivity.class);
        intent.putExtra("year", year);
        intent.putExtra("month", month);
        startActivity(intent);

        // empty the date edittext fields
        input_year.setText("");
        input_month.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("g53mdp", "MonthYearActivity onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("g53mdp", "MonthYearActivity onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("g53mdp", "MonthYearActivity onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("g53mdp", "MonthYearActivity onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("g53mdp", "MonthYearActivity onStop");
    }

}
