package com.example.recipebook;

import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context, "kcrDB", null, 1);
    }

    /*
    public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
        Log.d("g54mdp", "DBHelper");
    }
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("g54mdp", "onDBCreate");

        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE recipes (" +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                "name VARCHAR(128) NOT NULL,"+
                "instructions VARCHAR(128) NOT NULL,"+
                "rating INTEGER DEFAULT 0);");

        db.execSQL("CREATE TABLE ingredients ("+
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                "ingredientname VARCHAR(128) NOT NULL);");

        db.execSQL("CREATE TABLE recipe_ingredients ("+
                "recipe_id INT NOT NULL,"+
                "ingredient_id INT NOT NULL,"+
                "CONSTRAINT fk1 FOREIGN KEY (recipe_id) REFERENCES recipes (_id),"+
                "CONSTRAINT fk2 FOREIGN KEY (ingredient_id) REFERENCES ingredients (_id),"+
                "CONSTRAINT _id PRIMARY KEY (recipe_id, ingredient_id) );");

        db.execSQL("INSERT INTO recipes (name, instructions, rating) VALUES ('americano', 'put one espresso shot into the cup and pour some hot water', 3);");
        db.execSQL("INSERT INTO recipes (name, instructions, rating) VALUES ('latte', 'put one espresso shot into the cup and pour some hot milk', 5);");
        db.execSQL("INSERT INTO recipes (name, instructions) VALUES ('chips', 'put sliced popatoes into the oils');");

        db.execSQL("INSERT INTO ingredients (ingredientname) VALUES ('espresso');");
        db.execSQL("INSERT INTO ingredients (ingredientname) VALUES ('water');");
        db.execSQL("INSERT INTO ingredients (ingredientname) VALUES ('milk');");
        db.execSQL("INSERT INTO ingredients (ingredientname) VALUES ('potato');");
        db.execSQL("INSERT INTO ingredients (ingredientname) VALUES ('oil');");

        db.execSQL("INSERT INTO recipe_ingredients (recipe_id, ingredient_id) VALUES (1, 1);");
        db.execSQL("INSERT INTO recipe_ingredients (recipe_id, ingredient_id) VALUES (1, 2);");
        db.execSQL("INSERT INTO recipe_ingredients (recipe_id, ingredient_id) VALUES (2, 1);");
        db.execSQL("INSERT INTO recipe_ingredients (recipe_id, ingredient_id) VALUES (2, 3);");
        db.execSQL("INSERT INTO recipe_ingredients (recipe_id, ingredient_id) VALUES (3, 4);");
        db.execSQL("INSERT INTO recipe_ingredients (recipe_id, ingredient_id) VALUES (3, 5);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recipes");
        db.execSQL("DROP TABLE IF EXISTS ingredients");
        db.execSQL("DROP TABLE IF EXISTS recipe_ingredients");
        onCreate(db);
    }
}