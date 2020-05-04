package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SimpleCursorAdapter dataAdapter;
    DBHelper dbHelper;
    SQLiteDatabase db;
    //Handler h = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instantiate
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        // list recipes by title
        Cursor c = db.query("recipes", new String[] { "_id", "name", "rating" },
                null, null, null, null, null);

        String[] columns = new String[] {
                "name",
                "rating" };

        int[] to = new int[] {
                R.id.value1,
                R.id.value2, };

        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.title_layout, // layout for each row
                c, //cursor
                columns,
                to,
                0);

        final ListView listView = (ListView) findViewById(R.id.recipeList);
        listView.setAdapter(dataAdapter);

        // when each title clicked, shows single recipe
            // intent to SIngleRecipeActivity with the identifier

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // title 이 position 대로 넘어감 after sorting
                Cursor cursor;
                String sql = "SELECT * FROM recipes";
                cursor = db.rawQuery(sql, null);
                cursor.moveToPosition(position);
                String title = cursor.getString(cursor.getColumnIndex("name"));

                long key = listView.getItemIdAtPosition(position);
                //Toast.makeText(getApplicationContext(), key+"", Toast.LENGTH_SHORT).show();

                String _id = key+"";
                //Toast.makeText(getApplicationContext(), "recipe_id: "+_id , Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, SingleRecipeActivity.class);

                intent.putExtra("recipe_name", title);
                intent.putExtra("recipe_id", _id);
                //Toast.makeText(getApplicationContext(), "intent", Toast.LENGTH_SHORT).show();

                startActivity(intent);
                //Toast.makeText(getApplicationContext(), "intent", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    class ChangeObserver extends ContentObserver {

        public ChangeObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            this.onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            queryRecipes();
        }
    }

     */

    //query Recipes
    /*
    public void queryRecipes() {

        String[] projection = new String[] {
                "NAME", "RATING"
        };

        String colsToDisplay [] = new String[] {
                "NAME", "RATING"
        };

        int[] colResIds = new int[] {
                R.id.value1,
                R.id.value2,
        };

        Cursor cursor = getContentResolver().query(myProviderContract.RECIPES_URI, projection, null, null, null);

        dataAdapter = new SimpleCursorAdapter(
                this,
                R.layout.title_layout,
                cursor,
                colsToDisplay,
                colResIds,
                0);

        ListView listView = (ListView) findViewById(R.id.recipeList);
        listView.setAdapter(dataAdapter);
    }
    */

    // list all unique ingredients
    public void onButton1Click(View v){
        //intent to ingredient Activity
        Intent intent= new Intent(MainActivity.this, IngredientActivity.class);
        startActivity(intent);
    }

    // sort by ratings
    public void onButton2Click(View v){
        //recipelistview sorted by ratings

        // instantiate
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        // list recipes by title
        Cursor cursor = db.query("recipes", new String[] { "_id", "name", "rating" },
                null, null, null, null, "rating desc");

        String[] columns = new String[] {
                "name",
                "rating" };

        int[] to = new int[] {
                R.id.value1,
                R.id.value2, };

        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.title_layout, // layout for each row
                cursor, //cursor
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.recipeList);
        listView.setAdapter(dataAdapter);

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d("g53mdp", "MainActivity onDestroy");
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.d("g53mdp", "MainActivity onPause");
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.d("g53mdp", "MainActivity onResume");
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Log.d("g53mdp", "MainActivity onStart");
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        Log.d("g53mdp", "MainActivity onStop");
    }
}
