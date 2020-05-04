package com.example.runtracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SingleRecordActivity extends AppCompatActivity {
    /*
    SingleRecordActivity is to show records data of the specific day user wants.
    Here, record data contains the numerical data computated from logging procedure and tags user annotated.
     */

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlerecord);

        // get intent from TodayActivity and retrieve the specific date user wants to see
        Intent intent = getIntent();
        String year = intent.getStringExtra("year");
        String month = intent.getStringExtra("month");
        String day = intent.getStringExtra("day");

        // display the date to textFields
        TextView yy = (TextView)findViewById(R.id.singleYear);
        TextView mm = (TextView)findViewById(R.id.singleMonth);
        TextView dd = (TextView)findViewById(R.id.singleDay);
        yy.setText(year);
        mm.setText(month);
        dd.setText(day);

        // settings for the TextView to display records data
        TextView distanceToday = (TextView)findViewById(R.id.singleDistance);
        TextView avgspeedToday = (TextView)findViewById(R.id.singleAvgspeed);
        TextView feelingToday = (TextView)findViewById(R.id.singleFeeling);
        TextView weatherToday = (TextView)findViewById(R.id.singleWeather);
        TextView commentToday = (TextView)findViewById(R.id.singleComment);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        Cursor cursor;

        // query the records data of the day to records table
        String[] selectedDay = {year, month, day};
        cursor = db.rawQuery("SELECT * FROM records WHERE year = ? and month = ? and day = ?", selectedDay);

        // retrieve the record row of the day and display each column to the proper textView
        while (cursor.moveToNext()) {

            String dist = cursor.getString(4) ; //distance
            distanceToday.setText(dist);

            String avg = cursor.getString(5) ; //avgspeed
            avgspeedToday.setText(avg);

            String feel = cursor.getString(6) ; //feeling
            feelingToday.setText(feel);

            String weather_ = cursor.getString(7) ; //weather
            weatherToday.setText(weather_);

            String comment_ = cursor.getString(8) ; //comment
            commentToday.setText(comment_);
        }
    }

    // navigate to the activity to annotate the record of the day when the button pressed
    public void onAnnotateButtonClick(View v){

        // retrieve the date data to intent to pack date data to new intent to AnnotateActivity
        Intent intent_2 = getIntent();
        String year = intent_2.getStringExtra("year");
        String month = intent_2.getStringExtra("month");
        String day = intent_2.getStringExtra("day");

        //intent to AnnotateActivity
            // pass date data with intent
        Intent intent_3= new Intent(SingleRecordActivity.this, AnnotateActivity.class);
        intent_3.putExtra("year", year);
        intent_3.putExtra("month", month);
        intent_3.putExtra("day", day);
        startActivity(intent_3);
    }

    // navigate to the main activity
    public void onBackToMainClick(View v){

        //intent to MainActivity
        Intent intent_4= new Intent(SingleRecordActivity.this, MainActivity.class);
        startActivity(intent_4);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("g53mdp", "SingleRecordActivity onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("g53mdp", "SingleRecordActivity onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("g53mdp", "SingleRecordActivity onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("g53mdp", "SingleRecordActivity onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("g53mdp", "SingleRecordActivity onStop");
    }


}
