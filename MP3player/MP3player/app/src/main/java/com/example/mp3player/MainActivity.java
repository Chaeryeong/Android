package com.example.mp3player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import com.example.mp3player.MyService;

public class MainActivity extends AppCompatActivity {


    private MyService.MyBinder myService = null;


    private ServiceConnection serviceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("g53mdp", "MainActivity onServiceConnected");
            //Toast.makeText(getApplicationContext(), "MainActivity onServiceConnected", Toast.LENGTH_LONG).show();
            myService = (MyService.MyBinder) service;
            //myService.registerCallback(callback);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("g53mdp", "MainActivity onServiceDisconnected");
            //Toast.makeText(getApplicationContext(), "MainActivity onServiceDIsconnected", Toast.LENGTH_LONG).show();
            //myService.unregisterCallback(callback);
            myService = null;
        }
    };


    /*
       ICallback callback = new ICallback() {
        @Override
        public void counterEvent(final int counter) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView tv = (TextView) findViewById(R.id.textView);
                    tv.setText("counter: "+counter);
                }
            });
        }
    };
     */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind the service when first create the activity
        Intent intent = new Intent (getApplicationContext(), MyService.class);
        //startService(intent);
        this.bindService(new Intent(this, MyService.class), serviceConnection, Context.BIND_AUTO_CREATE);

        //for listing
        final ListView lv = (ListView) findViewById(R.id.ListView);

        File musicDir = new File(
                Environment.getExternalStorageDirectory().getPath()+ "/Music/");
        File list[] = musicDir.listFiles();

        lv.setAdapter(new ArrayAdapter<File>(this,
                android.R.layout.simple_list_item_1, list));


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick (AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {

                Log.d("g53mdp", "music selected!");

                //Intent intent = new Intent(MainActivity.this, MyService.class);
                //bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

                File selectedFromList =(File) (lv.getItemAtPosition(myItemInt));
                Log.d("g53mdp", selectedFromList.getAbsolutePath());

                myService.load(selectedFromList.getAbsolutePath());

                // display duration of the music
                int du = myService.getDuration();
                TextView tv = (TextView) findViewById(R.id.Duration);
                tv.setText(du+"");

            }
        });
    }

    public void onPlayButtonClick(View v){
        myService.Play();
    }

    public void onPauseButtonClick(View v){
        myService.Pause();
    }

    public void onStopButtonClick(View v){
        myService.Stop();

        if(serviceConnection!=null) {
            //unbindService(serviceConnection);
            serviceConnection = null;
        }
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

}








/*
Free MP3 file source: https://myfreemp3c.com/
 */

/*
not killing service after activity destroy

 Intent i = new Intent(MDService.class.getName());
        startService(i);
        bindService(i, mConnection, Context.BIND_AUTO_CREATE);
 */