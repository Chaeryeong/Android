package com.example.mp3player;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;
import android.widget.Toast;
import android.os.RemoteCallbackList;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.app.PendingIntent;

public class MyService extends Service {

    // notification
    private final String CHANNEL_ID = "100";

    private final IBinder binder = new MyBinder();
    //RemoteCallbackList<MyBinder> remoteCallbackList = new RemoteCallbackList<MyBinder>();

    // instantiating MP3Player class in an service component
    protected MP3Player mp3Player;

    int duration;

    /*
    public void doCallbacks(int count){
        final int n = remoteCallbackList.beginBroadcast();
        for(int i = 0; i<n; i++){
            remoteCallbackList.getBroadcastItem(i).callback.counterEvent(count);
        }
        remoteCallbackList.finishBroadcast();
    }*/


    public class MyBinder extends Binder
    { // defines the interface that your activity will be able to make use

        //@Override
        //public IBinder asBinder(){
        //    return this;
        //}

        void load(String filepath){
            Toast.makeText(getApplicationContext(), "Load the music", Toast.LENGTH_LONG).show();
            MyService.this.load(filepath);
        }

        void Play()
        {
            Toast.makeText(getApplicationContext(), "Unpause the music", Toast.LENGTH_LONG).show();
            MyService.this.Play();
        }
        void Pause()
        {
            Toast.makeText(getApplicationContext(), "Pause the music", Toast.LENGTH_LONG).show();
            MyService.this.Pause();
        }

        void Stop()
        {
            Toast.makeText(getApplicationContext(), "Stop playing the track", Toast.LENGTH_LONG).show();
            MyService.this.Pause();
        }

        int getDuration(){
            return MyService.this.getDuration();
        }



        /*
        public void registerCallback(ICallback callback){
            this.callback = callback;
            remoteCallbackList.register(MyBinder.this);
        }

        public void unregisterCallback(ICallback callback){
            this.callback = callback;
            remoteCallbackList.unregister(MyBinder.this);
        }

        ICallback callback;

            */

    }


    public void load(String filepath){
        //mp3Player.filePath = mp3Player.getFilePath();
        mp3Player.load(filepath);
    }

    public void Play(){
        mp3Player.play();
    }

    public void Pause(){
        mp3Player.pause();
    }

    public void Stop(){
        mp3Player.stop();
    }

    public int getDuration(){
        duration = mp3Player.getDuration();
        return duration;
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId){
        // everytime service starts
        return onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {

        super.onCreate();
        Log.d("g53mdp", "service onCreate");
        Toast.makeText(getApplicationContext(), "Service created!", Toast.LENGTH_LONG).show();

        mp3Player = new MP3Player();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        Log.d("g53mdp", "service onBind");
        Toast.makeText(getApplicationContext(), "Service bound!", Toast.LENGTH_LONG).show();

        // notification
        NotificationManager notificationManager = (NotificationManager)  getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel name";
            String description = "channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel);
        }

        int NOTIFICATION_ID = 001;

        Intent intent = new Intent(MyService.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("MP3Player notification")
                .setContentText("click to return to the activity")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        //startForeground(NOTIFICATION_ID, mBuilder.build());
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());

        return new MyBinder();
        //return null;
    }


    @Override
    public void onRebind(Intent intent) {
        // TODO Auto-generated method stub
        Log.d("g53mdp", "service onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub
        Log.d("g53mdp", "service onUnbind");
        mp3Player.stop();
        //stopForeground(true);
        return super.onUnbind(intent);
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Log.d("g53mdp", "service onDestroy");
        // release procedure
        mp3Player.stop();
        //stopForeground(true);
        super.onDestroy();
    }


}
