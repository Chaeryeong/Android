package com.example.runtracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {
    /*
    Activity to retrieve the date data(year, month, day) from users before they start logging movement data!
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    // navigate to the activity to start and stop logging&saving records plus, control service
    public void onStartRunButtonClick(View v){

        // retrieve the date data from user
        EditText input_year = (EditText)findViewById(R.id.inputYear);
        EditText input_month = (EditText)findViewById(R.id.inputMonth);
        EditText input_day = (EditText)findViewById(R.id.inputDay);
        String year = input_year.getText().toString();
        String month = input_month.getText().toString();
        String day = input_day.getText().toString();

        // pass the date data to log  through the intent to the activity with services to start logging & saving data
        Intent intent= new Intent(AddActivity.this, StartStopActivity.class);
        intent.putExtra("year", year);
        intent.putExtra("month", month);
        intent.putExtra("day", day);
        startActivity(intent);

        // empty the date editText fields
        input_year.setText("");
        input_month.setText("");
        input_day.setText("");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("g53mdp", "AddActivity onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("g53mdp", "AddActivity onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("g53mdp", "AddActivity onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("g53mdp", "AddActivity onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("g53mdp", "AddActivity onStop");
    }

}
