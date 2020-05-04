package com.example.recipebook;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class IngredientActivity extends AppCompatActivity{

    SimpleCursorAdapter dataAdapter;
    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        // list all unique ingredients to ingredientList
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        // list ***unique*** ingredients of the stored recipe
        Cursor cursor = db.rawQuery("select distinct i._id, i.ingredientname from recipes r join recipe_ingredients ri on (r._id = ri.recipe_id) join ingredients i on (ri.ingredient_id = i._id)", null);

        /*
        Cursor cursor = db.query("ingredients", new String[] { "_id", "ingredientname"},
                null, null, null, null, null);
         */

        String[] columns = new String[] {
                "i.ingredientname"};

        int[] to = new int[] {
                R.id.ingredient_name};

        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.ingredient_layout, // layout for each row
                cursor,
                columns,
                to,
                0);

        //Toast.makeText(getApplicationContext(), "hey", Toast.LENGTH_SHORT).show();

        ListView listView = (ListView) findViewById(R.id.ingredientList);
        listView.setAdapter(dataAdapter);



    }

}
