package com.example.runtracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class TodayActivity extends AppCompatActivity {
    /*
    TodayActivity is the activity to retrieve the date data(year, month, day) from users
    to be used to search specific record of the day in SIngleRecordData.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
    }

    // navigate to the activity to view your record of the day
    public void onFindButtonClick(View v){
        //intent to SingleRecordActivity

            // retrieve the date
        EditText find_year = (EditText)findViewById(R.id.findYear);
        EditText find_month = (EditText)findViewById(R.id.findMonth);
        EditText find_day = (EditText)findViewById(R.id.findDay);
        String year = find_year.getText().toString();
        String month = find_month.getText().toString();
        String day = find_day.getText().toString();

        // pass the date data to the SingleRecordActivity
        Intent intent= new Intent(TodayActivity.this, SingleRecordActivity.class);
        intent.putExtra("year", year);
        intent.putExtra("month", month);
        intent.putExtra("day", day);
        startActivity(intent);

        // empty the date edittext fields
        find_year.setText("");
        find_month.setText("");
        find_day.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("g53mdp", "TodayActivity onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("g53mdp", "TodayActivity onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("g53mdp", "TodayActivity onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("g53mdp", "TodayActivity onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("g53mdp", "TodayActivity onStop");
    }

}
