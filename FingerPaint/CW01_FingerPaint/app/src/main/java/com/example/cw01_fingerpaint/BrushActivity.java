package com.example.cw01_fingerpaint;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class BrushActivity extends AppCompatActivity {

    int brushType;
    int brushWidth;
    int chosen = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brush);

        // from intent - get current brush type & width and display
        Bundle bundle = getIntent().getExtras();
        brushType = bundle.getInt("brushType");
        brushWidth = bundle.getInt("brushWidth");
        TextView ShapeResult = (TextView) findViewById((R.id.ShapeResult));
        switch(brushType){
            case 1: // round
                ShapeResult.setText("Round");
                break;
            case 2: // square
                ShapeResult.setText("Square");
                break;
            default: // default_round
                ShapeResult.setText("Round");
        }
        TextView WidthResult = (TextView) findViewById((R.id.WidthResult));
        WidthResult.setText(brushWidth+"");
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

    public void onRoundClick(View v){
        chosen = 1; // round
        TextView decisionType = (TextView) findViewById((R.id.decideShape));
        decisionType.setText("Round");
    }

    public void onSquareClick(View v){
        chosen = 2; // square
        TextView decisionType = (TextView) findViewById((R.id.decideShape));
        decisionType.setText("Square");
    }

    public void onBrushConfirmClick(View v){
        //pass chosen brush type & width to main activity
            // If nth clicked/typed for brush shape or width when confirm button pressed.
                // default goes

        //using bundle to pass data to an activity
        Bundle bundle = new Bundle();
        /*
        if(chosen == 0){
            chosen = 1; //default - round
        } */
        bundle.putInt("newBrushType", chosen); //key, value

        EditText chosenWidth = (EditText) findViewById(R.id.Width);
        int newWidth;
        if(chosenWidth == null) {newWidth = 20;}
        else{
            newWidth = Integer.parseInt(chosenWidth.getText().toString());
        }
        bundle.putInt("newBrushWidth", newWidth); //key, value
        Intent result = new Intent();
        result.putExtras(bundle);
        setResult(Activity.RESULT_OK, result);

        finish();
    }
}
