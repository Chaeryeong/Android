package com.example.runtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    /*
    Broadcast Receiver that reponds to the broadcast.
    I specified the broadcast here to be the date noticement or announcement to check validity of the input date from other apps - e.g. calender app.
    and the reponse to be log the retrieved message from the broadcast intent and make toast so that user can notice.
     */

    @Override
    public void onReceive(Context context, Intent intent) {
        // an Intent broadcast.
        String name = intent.getAction();
        String message = intent.getStringExtra("dateToday");
        Log.d("g53mdp", "received message:" + message);
        Toast.makeText(context, "NOTE THAT TODAY IS: "+message , Toast.LENGTH_SHORT).show();
    }
}

