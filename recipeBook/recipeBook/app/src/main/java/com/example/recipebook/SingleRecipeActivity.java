package com.example.recipebook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SingleRecipeActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        // displays a single recipe
        Intent intent = getIntent();
        //String recipe_title = intent.getExtras().getString("recipe_name");
        String recipe_id = intent.getExtras().getString("recipe_id");
        //Toast.makeText(getApplicationContext(), recipe_id+"   "+recipe_title, Toast.LENGTH_SHORT).show();
        // id는 정확하게 넘어옴 *** title check!
        String [] recipeId = {recipe_id};

        TextView Title = (TextView)findViewById(R.id.title);
        TextView Instruction = (TextView)findViewById(R.id.instruction);
        TextView Rating = (TextView)findViewById(R.id.rating);

        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM recipes WHERE _id = ?", recipeId);

        while (cursor.moveToNext()) {

            String name = cursor.getString(1) ; //name
            //Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
            Title.setText(name);

            String instruc = cursor.getString(2) ; //instructions
            //Toast.makeText(getApplicationContext(), instruc, Toast.LENGTH_SHORT).show();
            Instruction.setText(instruc);

            String rate = cursor.getInt(3)+"" ; // rating
            Rating.setText(rate);
        }

        // list ingredients of the recipe
        Cursor c = db.rawQuery("select i.ingredientname from recipes r join recipe_ingredients ri on (r._id = ri.recipe_id) join ingredients i on (ri.ingredient_id = i._id) where r._id=?", recipeId);

        String resultRow = "";
        if(c.moveToFirst()) {
            do {
                String ingred = c.getString(0);

                resultRow = ""+resultRow+ingred+" ";

            } while(c.moveToNext()); }

        TextView result = (TextView) findViewById(R.id.ingredient);
        result.setText(resultRow);

    }

    // rate the recipe
    public void onButton5Click(View v){

        Intent intent = getIntent();
        String recipe_id = intent.getExtras().getString("recipe_id");

        //intent to ingredient Activity
        Intent intent_2= new Intent(SingleRecipeActivity.this, RateActivity.class);
        intent_2.putExtra("recipe_id", recipe_id);
        startActivity(intent_2);
    }

    // enter the new recipe
    public void onButton3Click(View v){

       // intent to the EnterActivity
        Intent intent_5= new Intent(SingleRecipeActivity.this, EnterActivity.class);
        startActivity(intent_5);
    }

    // delete the recipe
    public void onButton4Click(View v){

        Intent intent = getIntent();
        String recipe_id = intent.getExtras().getString("recipe_id");

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        //delete from recipes
        db.execSQL("DELETE FROM recipes WHERE _id = '" + recipe_id + "';");

        // after delete, go back to the list activity
        Intent intent_4= new Intent(SingleRecipeActivity.this, MainActivity.class);
        startActivity(intent_4);
    }

    // go back to the recipe list by title
    public void onBackButtonClick(View v){

        Intent intent_3= new Intent(SingleRecipeActivity.this, MainActivity.class);
        startActivity(intent_3);
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
