package com.example.runtracker;

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
        uriMatcher.addURI(myProviderContract.AUTHORITY, "logs", 1);
        uriMatcher.addURI(myProviderContract.AUTHORITY, "logs/#", 2);
        uriMatcher.addURI(myProviderContract.AUTHORITY, "records", 3);
        uriMatcher.addURI(myProviderContract.AUTHORITY, "records/#", 4);
    }

    @Override
    public boolean onCreate() {
        Log.d("g53mdp", "contentprovider oncreate");
        return true; //returning a handle to the underlying database
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        //determines the form of the data users of the contentprovider will get back

        String contentType;
        if (uri.getLastPathSegment()==null) {
            contentType = myProviderContract.CONTENT_TYPE_MULTIPLE;  // as dir in contract
        } else {
            contentType = myProviderContract.CONTENT_TYPE_SINGLE; // as item in contract
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
                tableName = "logs";
                break;
            case 3:
                tableName = "records";
                break;
            default:
                tableName = "records";
                break;
        }

        long id = db.insert(tableName, null, values);
        db.close();
        Uri nu = ContentUris.withAppendedId(uri, id);
        // returns new uri to identify the newly insert record

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
            case 2: // logs/#
                selection = "_ID = " + uri.getLastPathSegment();
            case 1: // logs
                return db.query("logs", projection, selection, selectionArgs, null, null, sortOrder);
            case 4: // records/#
                selection = "_ID = " + uri.getLastPathSegment();
            case 3: // records
                return db.query("records", projection, selection, selectionArgs, null, null, sortOrder);
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
