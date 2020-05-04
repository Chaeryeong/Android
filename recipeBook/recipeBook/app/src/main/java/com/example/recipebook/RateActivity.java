package com.example.recipebook;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RateActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        Intent intent = getIntent();
        String r_id = intent.getStringExtra("recipe_id");
        //Toast.makeText(getApplicationContext(), "recipe_id : "+r_id, Toast.LENGTH_SHORT).show();
    }

    // rate the recipe - update value
    public void onRateButtonClick(View v){

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        Intent intent = getIntent();
        String r_id = intent.getStringExtra("recipe_id");
        int id = Integer.parseInt(r_id);

        EditText rate = (EditText)findViewById(R.id.getRate);
        String rate_ = rate.getText().toString();
        //Toast.makeText(getApplicationContext(), rate_, Toast.LENGTH_SHORT).show();
        int newRate = Integer.parseInt(rate_);

        // update rating value into the recipe table
        ContentValues values = new ContentValues();
        values.put("rating",  newRate);    //carNumber를 변경하고자 할때
        db.update("recipes", values, "_id=?", new String[] {r_id});
        //db.execSQL("UPDATE recipes SET rating=rate_ WHERE _id=r_id;");

        Toast.makeText(getApplicationContext(), "success on rating", Toast.LENGTH_SHORT).show();

        rate.setText("");

        // after confirm, go back to the single recipe activity
        Intent intent_2 = new Intent(RateActivity.this, SingleRecipeActivity.class);
        intent_2.putExtra("recipe_id", r_id);
        //Toast.makeText(getApplicationContext(), "intent", Toast.LENGTH_SHORT).show();
        startActivity(intent_2);

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

}
