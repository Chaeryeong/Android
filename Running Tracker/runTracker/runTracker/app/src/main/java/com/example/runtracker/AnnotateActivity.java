package com.example.runtracker;

import android.content.ContentValues;
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

public class AnnotateActivity extends AppCompatActivity {
    /*
    Activity to annotate(add tags) your record of the specific day.
     */

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotate);

        // get intent from SingleRecordActivity and retrieve the date data
        Intent intent = getIntent();
        String year = intent.getStringExtra("year");
        String month = intent.getStringExtra("month");
        String day = intent.getStringExtra("day");

        TextView yy = (TextView)findViewById(R.id.annotateYear);
        TextView mm = (TextView)findViewById(R.id.annotateMonth);
        TextView dd = (TextView)findViewById(R.id.annotateDay);

        // display date on the textField
        yy.setText(year);
        mm.setText(month);
        dd.setText(day);

        // field to display record information on the day
        TextView distance = (TextView)findViewById(R.id.annotateDistance);
        TextView avgspeed = (TextView)findViewById(R.id.annotateAvgspeed);

        // field to retrieve string input from user
        EditText feeling = (EditText) findViewById(R.id.annotateFeeling);
        EditText weather = (EditText) findViewById(R.id.annotateWeather);
        EditText comment = (EditText) findViewById(R.id.annotateComment);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        Cursor cursor;

        String[] selectedDay = {year, month, day};
        cursor = db.rawQuery("SELECT * FROM records WHERE year = ? and month = ? and day = ?", selectedDay);

        // retrieve and display the record information of the day from records table
        while (cursor.moveToNext()) {

            String dist = cursor.getString(4) ; //distance
            distance.setText(dist);

            String avg = cursor.getString(5) ; //avgspeed
            avgspeed.setText(avg);

            String feel = cursor.getString(6) ; //feeling
            feeling.setText(feel);

            String weather_ = cursor.getString(7) ; //weather
            weather.setText(weather_);

            String comment_ = cursor.getString(8) ; //comment
            comment.setText(comment_);
        }
    }

    // add comments to the records table and navigate back to the SIngleRecordActivity to see any updates
    public void onUpdateButtonClick(View v){

        // get date intent to use as combined key to find the row to update
        Intent intent = getIntent();
        String year = intent.getStringExtra("year");
        String month = intent.getStringExtra("month");
        String day = intent.getStringExtra("day");

        // retrieve the input of feeling&weather&comment the user typed
        EditText feeling = (EditText) findViewById(R.id.annotateFeeling);
        EditText weather = (EditText) findViewById(R.id.annotateWeather);
        EditText comment = (EditText) findViewById(R.id.annotateComment);
        String newFeeling = feeling.getText().toString();
        String newWeather = weather.getText().toString();
        String newComment = comment.getText().toString();

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        Cursor cursor;

        String[] selectedDay = {year, month, day};

        // update the retrieved feeling&weather&comment to the records table
        ContentValues values = new ContentValues();
        values.put("feeling",  newFeeling);
        db.update("records", values, "year=? and month=? and day=?", selectedDay);
        ContentValues values_2 = new ContentValues();
        values_2.put("weather",  newWeather);
        db.update("records", values_2, "year=? and month=? and day=?", selectedDay);
        ContentValues values_3 = new ContentValues();
        values_3.put("comment",  newComment);
        db.update("records", values_3, "year=? and month=? and day=?", selectedDay);
        Toast.makeText(getApplicationContext(), "Annotation added!", Toast.LENGTH_SHORT).show();

        //intent to SingleRecordActivity
            // pass date with intent to be used for searching the right row & display updated tags
        Intent intent_2= new Intent(AnnotateActivity.this, SingleRecordActivity.class);
        intent_2.putExtra("year", year);
        intent_2.putExtra("month", month);
        intent_2.putExtra("day", day);
        startActivity(intent_2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("g53mdp", "AnnotateActivity onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("g53mdp", "AnnotateActivity onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("g53mdp", "AnnotateActivity onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("g53mdp", "AnnotateActivity onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("g53mdp", "AnnotateActivity onStop");
    }


}
