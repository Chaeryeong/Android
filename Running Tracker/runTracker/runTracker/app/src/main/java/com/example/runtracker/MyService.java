package com.example.runtracker;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MyService extends Service {
    /*
    The servcie keeps playing the music in the background while user is running & logging the movement data.
     */

    // instantiate MediaPlayer to play musics in background
    MediaPlayer mp;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("g53mdp", "Service on Create");

        mp = MediaPlayer.create(this, R.raw.handclap);
        mp.setLooping(false); // set music to be played repeatedly

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("g53mdp", "Service on Start");

        // start playing music
        mp.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("g53mdp", "Service on Stop");

        // stop playing music
        mp.stop();
        super.onDestroy();
    }
}
