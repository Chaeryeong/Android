package com.example.runtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    /*
    These main activity is like main menu.
    Displayed buttons are entry points to other activities!
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // navigate to the activity to start new run or walk
    public void onAddButtonClick(View v){
        //intent to AddActivity
        Intent intent_1= new Intent(MainActivity.this, AddActivity.class);
        startActivity(intent_1);
    }

    // navigate to the activity to view the list of your records
    public void onHistoryButtonClick(View v){
        //intent to HistoryActivity
        Intent intent_2= new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(intent_2);
    }

    // navigate to the activity to view records of the day & annotate tags
    public void onTodayButtonClick(View v){

        //send broadcast for TEST
        /*
        Intent intent = new Intent("com.example.runtracker.DATE_TODAY");
        intent.putExtra("dateToday", "CHECK FOR VALID DATE BEFORE TYPE!");
        sendBroadcast(intent);
         */

        //intent to TodayActivity
        Intent intent_3 = new Intent(MainActivity.this, TodayActivity.class);
        startActivity(intent_3);
    }

    // navigate to the activity to view your total records of the month and year
    public void onTotalButtonClick(View v){

        //intent to MonthYearActivity to get periods from users
        Intent intent_4 = new Intent(MainActivity.this, MonthYearActivity.class);
        startActivity(intent_4);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("g53mdp", "MainActivity onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("g53mdp", "MainActivity onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("g53mdp", "MainActivity onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("g53mdp", "MainActivity onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("g53mdp", "MainActivity onStop");
    }


    /*
    - Code & Resource References-
    All images: all take reference from Google Image.
    Music mp3 file: https://freemusicarchiver.org/static
    Partial code of getting permission for GPS: https://black-jin0427.tistory.com/23
     */
}

