package com.example.runtracker;

import android.net.Uri;

public class myProviderContract {

    public static final String AUTHORITY = "com.example.runtracker.myProvider";

    // nature of the uri
    public static final Uri RECIPES_URI = Uri.parse("content://"+AUTHORITY+"/logs");
    public static final Uri INGREDIENTS_URI = Uri.parse("content://"+AUTHORITY+"/records");

    // column names
    public static final String _ID = "_id";

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String TIME = "time";

    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";
    public static final String DISTANCE = "distance";
    public static final String AVGSPEED = "avgspeed";
    public static final String FEELING = "feeling";
    public static final String WEATHER = "weather";
    public static final String COMMENT = "comment";
    public static final String DURATION = "duration";

    public static final String CONTENT_TYPE_SINGLE = "vnd.android.cursor.item/myProvider.data.text";
    public static final String CONTENT_TYPE_MULTIPLE = "vnd.android.cursor.dir/myProvider.data.text";

}
