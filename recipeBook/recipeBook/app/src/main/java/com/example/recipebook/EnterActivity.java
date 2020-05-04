package com.example.recipebook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EnterActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
    }

    // enter the new recipe
    public void onConfirmButtonClick(View v){

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        EditText str_1 = (EditText)findViewById(R.id.editText1); // title
        EditText str_2 = (EditText)findViewById(R.id.editText2); // instruction
        EditText str_3 = (EditText)findViewById(R.id.editText3); // ingredient

        String input_name = str_1.getText().toString();
        String input_instruc = str_2.getText().toString();
        String input_ingreds = str_3.getText().toString();

        // split the ingredients name & store them to the array
        //Toast.makeText(getApplicationContext(), input_ingreds, Toast.LENGTH_SHORT).show();

        String ingredients[] = input_ingreds.split("\\s");
        /*
        for(String ingredient : ingredients){
            //Log.d("g53mdp", ingredient); // check log
            Toast.makeText(getApplicationContext(), ingredient, Toast.LENGTH_SHORT).show();
         }
         */

        /*
        insert name & instruc
        ingredient - each of them check if already exist in ingredients
                    1 - exist: retrieve the ingre_id
                    2 - not: add & retrieve the _id
         retrieve recipe_id => insert recipe_id&each of ingre_id to the recipe_ingredients table
         */

        db.execSQL("INSERT INTO recipes (name, instructions) VALUES ('" + input_name + "','" + input_instruc + "');");

        // retrieve the recipe_id from the recipes table
        Cursor cursor;
        //String[] recipeTitle = {input_name};

        cursor = db.rawQuery("SELECT * FROM recipes;", null);
        cursor.moveToLast();
        String recipe_ID = cursor.getString(0);
        //Toast.makeText(getApplicationContext(), recipe_ID, Toast.LENGTH_SHORT).show();

        /*
        for each of the ingredient name, check already exists
            if exist -> retrieve id & add recipe_id & ingred_id to the recipe_ingredients
            if not exist -> add ingredients, retrieve ingred_id & add to the recipe_ingredients
         */
        // --------------------------------------------------------------------
        for(String ingredient : ingredients){
            // checking existence of the data: ingredient_name
            String compared[] = {ingredient};

           // Cursor c =db.rawQuery("SELECT * FROM ingredients WHERE ingredientname=?", compared);
           // String ccc = c.getString(1);

            String[] columns={"_id, ingredientname"};
            Cursor result=db.query("ingredients", columns, "ingredientname=?", compared, null, null, null);

            //String ccc = result.getString(0);
            int count = result.getCount(); //works well

            if (count != 0)
            {
                // exists
                result.moveToFirst();
                Long ingred_ID = result.getLong(0);
                //Toast.makeText(getApplicationContext(), ingred_ID+"", Toast.LENGTH_SHORT).show();
                String ingred_id = ingred_ID+"";
                db.execSQL("INSERT INTO recipe_ingredients (recipe_id, ingredient_id) VALUES ('" + recipe_ID+ "','" + ingred_id + "');");
            }
            else
            {
                // not exist
                db.execSQL("INSERT INTO ingredients (ingredientname) VALUES ('" + ingredient + "');");
                Cursor c_2 =db.rawQuery("SELECT * FROM ingredients;", null);
                c_2.moveToLast();
                String ingred_id = c_2.getString(0); //newly added ingredients._id
                db.execSQL("INSERT INTO recipe_ingredients (recipe_id, ingredient_id) VALUES ('" + recipe_ID + "','" + ingred_id + "');");
            }

        }


        str_1.setText("");
        str_2.setText("");
        str_3.setText("");

        // intent to the Single recipe activity
        Intent intent_6= new Intent(EnterActivity.this, SingleRecipeActivity.class);
        intent_6.putExtra("recipe_id", recipe_ID);
        startActivity(intent_6);
    }

}
