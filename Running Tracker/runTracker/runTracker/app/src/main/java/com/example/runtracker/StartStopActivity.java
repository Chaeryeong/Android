package com.example.runtracker;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class StartStopActivity extends AppCompatActivity {
    /*
    StartStopActivity fucntionalities are listed below.
        - controls (start & stop) the service
        - log the movement data
        - save the computated record data when logging is done
     */

    DBHelper dbHelper;
    SQLiteDatabase db;

    // setting a variable used for the request of the permission
    private static int REQUEST_ACCESS_FINE_LOCATION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startstop);
    }

    // start services&logging
    public void onStartButtonClick(View v){

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        LocationManager locationManager =    (LocationManager)getSystemService(Context.LOCATION_SERVICE);

         /*
        checking permission needed in the runtime code although manifest contains permission code
        due to higher OS version than Marshmallow
         */
        // codes to check permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

            if(permissionCheck == PackageManager.PERMISSION_DENIED){

                // no permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_ACCESS_FINE_LOCATION);

            } else{
                // already have a permission for ACCESS_FINE_LOCATION
            }
        }
        else{
            // do not check permissions in the case of lower version than Marshmallow
        }

        // announce to user using toast
        Toast.makeText(getApplicationContext(), "YOU ARE NOW RUNNING!", Toast.LENGTH_SHORT).show();

        // start logging & service
        Intent intent_1 = new Intent(StartStopActivity.this, MyService.class);
        startService(intent_1);

        // empty logs table before use
        String empty_logs_sql = "DELETE FROM logs;";
        db.execSQL(empty_logs_sql);

        // instantiating LocationListener class to get location change reported and log the data
        final LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {

                // logs latitude&longitude updates
                Log.d("g53mdp", location.getLatitude() + " " + location.getLongitude());

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                long time = location.getTime();

                // when every update report, saves the log datat into logs table so that it can be used to compute meaningful numerics
                db.execSQL("INSERT INTO logs (latitude, longitude, time) VALUES ('" + latitude + "','" + longitude + "', '" + time + "');");
            }

            public void onProviderDisabled(String provider) {
                // the user disabled (for example) the GPS
                Log.d("g53mdp", "onProviderDisabled: " + provider);
            }

            public void onProviderEnabled(String provider) {
                // the user enabled (for example) the GPS
                Log.d("g53mdp", "onProviderEnabled: " + provider);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                // information about the signal, i.e. number of satellites
                Log.d("g53mdp", "onStatusChanged: " + provider + " " + status);
            }
        };

        try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        5, // minimum time interval between updates
                        5, // minimum distance between updates, in metres
                        locationListener);
        } catch(SecurityException e) {
            Log.d("g53mdp", e.toString());
        }


    }

    // stop services&logging
    public void onStopButtonClick(View v){

        // announce to user using toast
        Toast.makeText(getApplicationContext(), "YOU ENDED YOUR RUNNING!", Toast.LENGTH_SHORT).show();

        // finish logging&service
        Intent intent_2 = new Intent(StartStopActivity.this, MyService.class);
        stopService(intent_2);

        // get intent from the AddActivity(previous) to use when inserting/merging the new records
        Intent intent_3 = getIntent();
        String year = intent_3.getStringExtra("year");
        String month = intent_3.getStringExtra("month");
        String day = intent_3.getStringExtra("day");
        int yy = Integer.parseInt(year);
        int mm = Integer.parseInt(month);
        int dd = Integer.parseInt(day);

        String[] today = {year, month, day};

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        Cursor cursor;

        // total distance, average speed information needed

            // retrieve the first and the last logs
        cursor = db.rawQuery("SELECT * FROM logs", null);
        cursor.moveToFirst();
        double firstLatitude = cursor.getDouble(1); // get first latitude
        double firstLongitude = cursor.getDouble(2); // get first longitude
        long startTime = cursor.getLong(3); // get the start time
        cursor.moveToLast();
        double lastLatitude = cursor.getDouble(1); // get last latitude
        double lastLongitude = cursor.getDouble(2); // get last longitude
        long finishTime = cursor.getLong(3); // get the finish time

            // compute the total distance
        double distance = 0;
        Location locationA = new Location("A");
        locationA.setLatitude(firstLatitude);
        locationA.setLongitude(firstLongitude);
        Location locationB = new Location("B");
        locationB.setLatitude(lastLatitude);
        locationB.setLongitude(lastLongitude);
        distance = locationB.distanceTo(locationA); // linear distance(meter) from start point to stop point

            // to get average speed by the formula : distance/(time*1000) => m/s speed * 3.6 => km/h
        double avgSpeed = 0;
        long durationTime = finishTime - startTime;
        avgSpeed = (distance/(durationTime/1000))*3.6;

        // save the useful data from the logging to records table
          /*
       When inserting records into records table,
       if the record of the today exist -> merge it with the previous records of the day
       otherwise (first record of the day) -> insert
        */

        String[] columns={"_id, year, month, day, distance, avgspeed, duration"};
        Cursor count;
        count=db.query("records", columns, "year=? and month=? and day=?", today, null, null, null);
        int cnt = count.getCount();

        if (cnt != 0)
        {
            // exists the record of the day -> need merging & update of distance, avgspeed, duration
            count.moveToFirst();
            long previous_duration = count.getLong(6); // duration
            double previous_distance = count.getDouble(4); // distance

            long final_duration = 0;
            double final_avgspeed = 0;
            double final_distance = 0;

            final_distance = previous_distance+distance;
            final_duration = previous_duration+durationTime;
            final_avgspeed = (final_distance/(final_duration/1000))*3.6;

            // update to records table
            ContentValues values = new ContentValues();
            values.put("distance",  final_distance);
            db.update("records", values, "year=? and month=? and day=?", today);
            ContentValues values_2 = new ContentValues();
            values_2.put("avgspeed",  final_avgspeed);
            db.update("records", values_2, "year=? and month=? and day=?", today);
            ContentValues values_3 = new ContentValues();
            values_3.put("duration",  final_duration);
            db.update("records", values_3, "year=? and month=? and day=?", today);

        }
        else
        {
            // not exist -> insert new records
            db.execSQL("INSERT INTO records (year, month, day, distance, avgspeed, duration) VALUES ('" + yy + "','" + mm + "','" + dd + "','" + distance + "','" + avgSpeed + "','" + durationTime + "');");
        }

        // empty logs table after use
        String empty_logs_sql = "DELETE FROM logs;";
        db.execSQL(empty_logs_sql);

        // intent to go back to the main menu
        Intent intent= new Intent(StartStopActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /*
    after pop-up about allowing permissions, the return data is sent to the method below
    requestCode would be 1000 which was set before
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // grantResults[0] denied -> -1
        // grantResults[0] allowed -> 0 (PackageManager.PERMISSION_GRANTED)

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // given a permission for ACCESS_FINE_LOCATION

        } else {
            // permission for ACCESS_FINE_LOCATION denied

        }
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
