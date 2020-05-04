package com.example.recipebook;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.content.UriMatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.FileNotFoundException;

public class myProvider extends ContentProvider {

    private DBHelper dbHelper = null;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(myProviderContract.AUTHORITY, "recipes", 1);
        uriMatcher.addURI(myProviderContract.AUTHORITY, "recipes/#", 2);
        uriMatcher.addURI(myProviderContract.AUTHORITY, "ingredients", 3);
        uriMatcher.addURI(myProviderContract.AUTHORITY, "ingredients/#", 4);
        uriMatcher.addURI(myProviderContract.AUTHORITY, "recipe_ingredients", 5);
        uriMatcher.addURI(myProviderContract.AUTHORITY, "recipe_ingredients/#", 6);
        uriMatcher.addURI(myProviderContract.AUTHORITY, "*", 7);
    }

    @Override
    public boolean onCreate() {
        Log.d("g53mdp", "contentprovider oncreate");
        //this.dbHelper = new DBHelper(this.getContext(), "mydb", null, 7);
        return true; //returning a handle to the underlying database
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        //determines the form of the data users of the contentprovider will get back

        String contentType;
        if (uri.getLastPathSegment()==null) {
            contentType = myProviderContract.CONTENT_TYPE_MULTIPLE;  // contract에 dir로
        } else {
            contentType = myProviderContract.CONTENT_TYPE_SINGLE; // contract에 item으로
        }

        return contentType;
    }

    // methods - perform the operations on the db & return the results
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName;

        switch(uriMatcher.match(uri)) {
            case 1:
                tableName = "recipes";
                break;
            case 3:
                tableName = "ingredients";
                break;
            case 5:
                tableName = "recipe_ingredients";
                break;
            default:
                tableName = "recipes";
                break;
        }

        long id = db.insert(tableName, null, values);
        db.close();
        Uri nu = ContentUris.withAppendedId(uri, id);
        // returns new uri to identif the newly insert record

        Log.d("g53mdp", nu.toString());

        getContext().getContentResolver().notifyChange(nu, null);

        return nu;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d("g53mdp", uri.toString() + " " + uriMatcher.match(uri));

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch(uriMatcher.match(uri)) {
            case 2: // recipes/# ???
                selection = "_ID = " + uri.getLastPathSegment();
            case 1: // recipes
                return db.query("recipes", projection, selection, selectionArgs, null, null, sortOrder);
            case 4: // ingredients/# ???
                selection = "_ID = " + uri.getLastPathSegment();
            case 3: // ingredients
                return db.query("ingredients", projection, selection, selectionArgs, null, null, sortOrder);
            case 5: // recipe_ingredients ??
                //SELECT ingredientname FROM ~, ingredients ON ingredients._id = ~.recipe_ingredients.ingredient_id
                return  db.rawQuery("SELECT r._id as recipe_id, r.name, ri.ingredient_id, i.ingredientname "
                        +"from recipes r "+
                        "join recipe_ingredients ri on (r._id = ri.recipe_id)"+
                        "join ingredients i on (ri.ingredient_id = i._id) where r._id == ?", selectionArgs); //recipe ID
            case 6: // recipe_ingredients/# ???
                //String q6 = "SELECT _id, name, food FROM people UNION SELECT _id, name, food FROM animals WHERE _ID = " + uri.getLastPathSegment();
                return null;//db.rawQuery(q6, selectionArgs);
            case 7: // * ????
                String q7 = "SELECT * FROM recipes UNION SELECT * FROM ingredients UNION SELECT * FROM recipe_ingredients";
                return db.rawQuery(q7, selectionArgs);
            default:
                return null;
        }

    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        throw new UnsupportedOperationException("not implemented");
    }

}
