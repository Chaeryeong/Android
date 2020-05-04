package com.example.cw01_fingerpaint;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ColourActivity extends AppCompatActivity {
    /*
    -At first: default - Black
    -When come back: chosen colour should remain until new choice!
    */
    //TextView currentColour = (TextView) findViewById((R.id.currentColour));
    int currentColourNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour);

        // from intent - get current colour & num and display
        Bundle bundle = getIntent().getExtras();
        currentColourNum = bundle.getInt("colourNum");
        TextView currentColour = (TextView) findViewById((R.id.currentColour));
        switch(currentColourNum){
            case 1:
                currentColour.setText("Red");
                break;
            case 2:
                currentColour.setText("Green");
                break;
            case 3:
                currentColour.setText("Blue");
                break;
            case 4:
                currentColour.setText("Yellow");
                break;
            case 5:
                currentColour.setText("Black");
                break;
            default:
                currentColour.setText("Black");
        }
    }

    //@Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //@Override
    protected void onPause() {
        super.onPause();
    }

    //@Override
    protected void onResume() {
        super.onResume();
    }

    //@Override
    protected void onStart() {
        super.onStart();
    }

    //@Override
    protected void onStop() {
        super.onStop();
    }

    /*
    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("backgroundColour", backgroundColour);
    }
    @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        backgroundColour = savedInstanceState.getInt("backgroundColour");
    } */


    public void onRedClick(View v){
        TextView currentColour = (TextView) findViewById((R.id.currentColour));
        currentColour.setText("Red");
        currentColourNum = 1;
    }
    public void onGreenClick(View v){
        TextView currentColour = (TextView) findViewById((R.id.currentColour));
        currentColour.setText("Green");
        currentColourNum = 2;
    }
    public void onBlueClick(View v){
        TextView currentColour = (TextView) findViewById((R.id.currentColour));
        currentColour.setText("Blue");
        currentColourNum = 3;
    }
    public void onYellowClick(View v){
        TextView currentColour = (TextView) findViewById((R.id.currentColour));
        currentColour.setText("Yellow");
        currentColourNum = 4;
    }
    public void onBlackClick(View v){
        TextView currentColour = (TextView) findViewById((R.id.currentColour));
        currentColour.setText("Black");
        currentColourNum = 5;
    }

    public void onConfirmClick(View v){
        //pass currentColorNumber to main activity

        //using bundle to pass data to an activity
        Bundle bundle = new Bundle();
        bundle.putInt("chosenColourNum", currentColourNum); //key, value
        Intent result = new Intent();
        result.putExtras(bundle);
        setResult(Activity.RESULT_OK, result);

        //startActivity(result);
        finish();
    }
}
