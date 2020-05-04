package com.example.cw01_fingerpaint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final int ACTIVITY_COLOUR_REQUEST_CODE = 1;
    static final int ACTIVITY_BRUSH_REQUEST_CODE = 2;
    // default settings
    int currentColourNum = 5;
    int prevColourNum = 5;
    int brushTypeNum = 1;
    int prevBrushTypeNum = 1;
    int brushWidth = 20;
    int prevBrushWidth = 20;
    // FingerPainterView myFingerPainterView = (FingerPainterView) findViewById(R.id.canvas);

    @Override
    protected void onCreate(Bundle savedInstanceState) { // also onCreate called when rotation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* -- how to!!
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("/sdcard/Download/IMG_0464.JPG"));
        FingerPainterView myFingerPainter = (FingerPainterView) findViewById(R.id.canvas);
        myFingerPainter.load(intent.getData());
         */
        // default settings
        /*
        currentColourNum = prevColourNum = 5;
        brushTypeNum = prevBrushTypeNum = 1;
        brushWidth = prevBrushWidth = 20; */

        // default info for paint&brush
        /*
        TextView defaultColour = (TextView) findViewById((R.id.CurrColour));
        defaultColour.setText("Black");
        TextView defaultShape = (TextView) findViewById((R.id.CurrType));
        defaultShape.setText("Round");
        TextView defaultWidth = (TextView) findViewById((R.id.CurrWidth));
        defaultWidth.setText("20 px"); */

        // herehere

        switch(brushTypeNum){
            case 1: // round
                //myFingerPainterView.setBrush(Paint.Cap.ROUND);
                TextView currShape = (TextView) findViewById((R.id.CurrType));
                currShape.setText("Round");
                break;
            case 2: // square
                //myFingerPainterView.setBrush((Paint.Cap.SQUARE));
                TextView currShape_2 = (TextView) findViewById((R.id.CurrType));
                currShape_2.setText("Square");
                break;
            default: // default_Round
                //myFingerPainterView.setBrush(Paint.Cap.ROUND);
                TextView currShape_d = (TextView) findViewById((R.id.CurrType));
                currShape_d.setText("Round");
        }
        //myFingerPainterView.setBrushWidth(brushWidth);
        TextView currWidth = (TextView) findViewById((R.id.CurrWidth));
        currWidth.setText(brushWidth+" px");
        switch (currentColourNum){
            case 1: // Red
                //myFingerPainterView.setColour(0xFFFF0000);
                TextView currColour = (TextView) findViewById((R.id.CurrColour));
                currColour.setText("Red");
                break;
            case 2: // Green
                //myFingerPainterView.setColour(0xFF00FF00);
                TextView currColour_2 = (TextView) findViewById((R.id.CurrColour));
                currColour_2.setText("Green");
                break;
            case 3: // Blue
                //myFingerPainterView.setColour(0xFF0000FF);
                TextView currColour_3 = (TextView) findViewById((R.id.CurrColour));
                currColour_3.setText("Blue");
                break;
            case 4: // Yellow
                //myFingerPainterView.setColour(0xFFFFEB3B);
                TextView currColour_4 = (TextView) findViewById((R.id.CurrColour));
                currColour_4.setText("Yellow");
                break;
            case 5: // Black
                //myFingerPainterView.setColour(0xFF000000);
                TextView currColour_5 = (TextView) findViewById((R.id.CurrColour));
                currColour_5.setText("Black");
                break;
            default: // Black for default
                //myFingerPainterView.setColour(0xFF000000);
                TextView currColour_d = (TextView) findViewById((R.id.CurrColour));
                currColour_d.setText("Black");
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

        // home button pressed -> resume: still remain settings
    }

    //@Override
    protected void onStart() {

        super.onStart();
    }

    //@Override
    protected void onStop() {
        super.onStop();
    }


    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("prevColourNum", prevColourNum);
        outState.putInt("prevBrushTypeNum", prevBrushTypeNum);
        outState.putInt("prevBrushWidth", prevBrushWidth);
    }
    @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        prevColourNum = savedInstanceState.getInt("prevColourNum");
        prevBrushTypeNum = savedInstanceState.getInt("prevBrushTypeNum");
        prevBrushWidth = savedInstanceState.getInt("prevBrushWidth");
    }



    //@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        FingerPainterView myFingerPainterView = (FingerPainterView) findViewById(R.id.canvas);
        if(requestCode == ACTIVITY_COLOUR_REQUEST_CODE){
            // when getting result from ColourActivity
                // *** should maintain brush settings
            //여기
            brushWidth = prevBrushWidth;
            brushTypeNum = prevBrushTypeNum;
            switch(brushTypeNum){
                case 1: // round
                    myFingerPainterView.setBrush(Paint.Cap.ROUND);
                    TextView currShape = (TextView) findViewById((R.id.CurrType));
                    currShape.setText("Round");
                    break;
                case 2: // square
                    myFingerPainterView.setBrush((Paint.Cap.SQUARE));
                    TextView currShape_2 = (TextView) findViewById((R.id.CurrType));
                    currShape_2.setText("Square");
                    break;
                default: // default_Round
                    myFingerPainterView.setBrush(Paint.Cap.ROUND);
                    TextView currShape_d = (TextView) findViewById((R.id.CurrType));
                    currShape_d.setText("Round");
            }
            myFingerPainterView.setBrushWidth(brushWidth);
            TextView currWidth = (TextView) findViewById((R.id.CurrWidth));
            currWidth.setText(brushWidth+" px");
            //여기
            if(resultCode == RESULT_OK){
               // retrieve the chosen colour number and set colour
                Bundle bundle = data.getExtras();
                currentColourNum = bundle.getInt("chosenColourNum");
                switch (currentColourNum){
                    case 1: // Red
                        myFingerPainterView.setColour(0xFFFF0000);
                        TextView currColour = (TextView) findViewById((R.id.CurrColour));
                        currColour.setText("Red");
                        break;
                    case 2: // Green
                        myFingerPainterView.setColour(0xFF00FF00);
                        TextView currColour_2 = (TextView) findViewById((R.id.CurrColour));
                        currColour_2.setText("Green");
                        break;
                    case 3: // Blue
                        myFingerPainterView.setColour(0xFF0000FF);
                        TextView currColour_3 = (TextView) findViewById((R.id.CurrColour));
                        currColour_3.setText("Blue");
                        break;
                    case 4: // Yellow
                        myFingerPainterView.setColour(0xFFFFEB3B);
                        TextView currColour_4 = (TextView) findViewById((R.id.CurrColour));
                        currColour_4.setText("Yellow");
                        break;
                    case 5: // Black
                        myFingerPainterView.setColour(0xFF000000);
                        TextView currColour_5 = (TextView) findViewById((R.id.CurrColour));
                        currColour_5.setText("Black");
                        break;
                    default: // Black for default
                        myFingerPainterView.setColour(0xFF000000);
                        TextView currColour_d = (TextView) findViewById((R.id.CurrColour));
                        currColour_d.setText("Black");
                }
            }
            else if(resultCode == RESULT_CANCELED){
                // keep the previous colour! - not pressing confirm button and go back
                    // issue** how to maintain previous colour??
                currentColourNum = prevColourNum;
                switch (currentColourNum){
                    case 1: // Red
                        myFingerPainterView.setColour(0xFFFF0000);
                        TextView currColour = (TextView) findViewById((R.id.CurrColour));
                        currColour.setText("Red");
                        break;
                    case 2: // Green
                        myFingerPainterView.setColour(0xFF00FF00);
                        TextView currColour_2 = (TextView) findViewById((R.id.CurrColour));
                        currColour_2.setText("Green");
                        break;
                    case 3: // Blue
                        myFingerPainterView.setColour(0xFF0000FF);
                        TextView currColour_3 = (TextView) findViewById((R.id.CurrColour));
                        currColour_3.setText("Blue");
                        break;
                    case 4: // Yellow
                        myFingerPainterView.setColour(0xFFFFEB3B);
                        TextView currColour_4 = (TextView) findViewById((R.id.CurrColour));
                        currColour_4.setText("Yellow");
                        break;
                    case 5: // Black
                        myFingerPainterView.setColour(0xFF000000);
                        TextView currColour_5 = (TextView) findViewById((R.id.CurrColour));
                        currColour_5.setText("Black");
                        break;
                    default: // Black for default
                        myFingerPainterView.setColour(0xFF000000);
                        TextView currColour_d = (TextView) findViewById((R.id.CurrColour));
                        currColour_d.setText("Black");
                }
            }
        } else if(requestCode == ACTIVITY_BRUSH_REQUEST_CODE){
            // should maintain colour setting
            // 여기
            currentColourNum = prevColourNum;
            switch (currentColourNum){
                case 1: // Red
                    myFingerPainterView.setColour(0xFFFF0000);
                    TextView currColour = (TextView) findViewById((R.id.CurrColour));
                    currColour.setText("Red");
                    break;
                case 2: // Green
                    myFingerPainterView.setColour(0xFF00FF00);
                    TextView currColour_2 = (TextView) findViewById((R.id.CurrColour));
                    currColour_2.setText("Green");
                    break;
                case 3: // Blue
                    myFingerPainterView.setColour(0xFF0000FF);
                    TextView currColour_3 = (TextView) findViewById((R.id.CurrColour));
                    currColour_3.setText("Blue");
                    break;
                case 4: // Yellow
                    myFingerPainterView.setColour(0xFFFFEB3B);
                    TextView currColour_4 = (TextView) findViewById((R.id.CurrColour));
                    currColour_4.setText("Yellow");
                    break;
                case 5: // Black
                    myFingerPainterView.setColour(0xFF000000);
                    TextView currColour_5 = (TextView) findViewById((R.id.CurrColour));
                    currColour_5.setText("Black");
                    break;
                default: // Black for default
                    myFingerPainterView.setColour(0xFF000000);
                    TextView currColour_d = (TextView) findViewById((R.id.CurrColour));
                    currColour_d.setText("Black");
            }
            // 여기
            if(resultCode == RESULT_OK){
                // set newly confirmed brush type and width
                Bundle bundle_2= data.getExtras();
                brushTypeNum = bundle_2.getInt("newBrushType");
                brushWidth = bundle_2.getInt("newBrushWidth");
                switch(brushTypeNum){
                    case 1: // round
                        myFingerPainterView.setBrush(Paint.Cap.ROUND);
                        TextView currShape = (TextView) findViewById((R.id.CurrType));
                        currShape.setText("Round");
                        break;
                    case 2: // square
                        myFingerPainterView.setBrush((Paint.Cap.SQUARE));
                        TextView currShape_2 = (TextView) findViewById((R.id.CurrType));
                        currShape_2.setText("Square");
                        break;
                    default: // default_Round
                        myFingerPainterView.setBrush(Paint.Cap.ROUND);
                        TextView currShape_d = (TextView) findViewById((R.id.CurrType));
                        currShape_d.setText("Round");
                }
                myFingerPainterView.setBrushWidth(brushWidth);
                TextView currWidth = (TextView) findViewById((R.id.CurrWidth));
                currWidth.setText(brushWidth+" px");

            }else if(resultCode == RESULT_CANCELED){
                // keep the previous brush type and width??
                brushTypeNum = prevBrushTypeNum;
                brushWidth = prevBrushWidth;
                switch(brushTypeNum){
                    case 1: // round
                        myFingerPainterView.setBrush(Paint.Cap.ROUND);
                        TextView currShape = (TextView) findViewById((R.id.CurrType));
                        currShape.setText("Round");
                        break;
                    case 2: // square
                        myFingerPainterView.setBrush((Paint.Cap.SQUARE));
                        TextView currShape_2 = (TextView) findViewById((R.id.CurrType));
                        currShape_2.setText("Square");
                        break;
                    default: // default_Round
                        myFingerPainterView.setBrush(Paint.Cap.ROUND);
                        TextView currShape_d = (TextView) findViewById((R.id.CurrType));
                        currShape_d.setText("Round");
                }
                myFingerPainterView.setBrushWidth(brushWidth);
                TextView currWidth = (TextView) findViewById((R.id.CurrWidth));
                currWidth.setText(brushWidth+" px");

            }
        }
    }

    public void onColourClick(View v){
        // toss the current info to the activity of choosing colours of the paint
        prevColourNum = currentColourNum; // back up
        prevBrushTypeNum = brushTypeNum; // back up
        prevBrushWidth = brushWidth;
        Bundle bundle = new Bundle();
        bundle.putInt("colourNum", currentColourNum);
        Intent intent = new Intent(MainActivity.this, ColourActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, ACTIVITY_COLOUR_REQUEST_CODE);
    }


    public void onBrushClick(View v){
        // toss to the activity of changing type and size of the brush
        // brush shape and width(int)
        prevBrushTypeNum = brushTypeNum; // back up
        prevBrushWidth = brushWidth;
        prevColourNum = currentColourNum; // back up
        Bundle bundleBrush = new Bundle();
        bundleBrush.putInt("brushType", brushTypeNum);
        bundleBrush.putInt("brushWidth", brushWidth);
        Intent intentBrush = new Intent(MainActivity.this, BrushActivity.class);
        intentBrush.putExtras(bundleBrush);
        startActivityForResult(intentBrush, ACTIVITY_BRUSH_REQUEST_CODE);
    }
}


/*
- references
1. URL: https://iw90.tistory.com/153
   for the method not to call onCreate() when rotating the device
*/