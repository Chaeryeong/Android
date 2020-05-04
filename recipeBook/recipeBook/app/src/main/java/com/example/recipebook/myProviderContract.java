package com.example.recipebook;

import android.net.Uri;

public class myProviderContract {

    public static final String AUTHORITY = "com.example.recipebook.myProvider";

    // nature of the uri
    public static final Uri RECIPES_URI = Uri.parse("content://"+AUTHORITY+"/recipes");
    public static final Uri INGREDIENTS_URI = Uri.parse("content://"+AUTHORITY+"/ingredients");
    public static final Uri RECIPE_INGREDIENT_URI = Uri.parse("content://"+AUTHORITY+"/recipe_ingredients");
    public static final Uri ALL_URI = Uri.parse("content://"+AUTHORITY+"/");


    // column names
    public static final String _ID = "_id";

    public static final String NAME = "name";
    public static final String INSTRUCTIONS = "instructions";
    public static final String RATING = "rating";
    public static final String INGREDIENTNAME = "ingredientname";

    public static final String RECIPE_ID = "recipe_id";
    public static final String INGREDIENT_ID = "ingredient_id";

    public static final String CONTENT_TYPE_SINGLE = "vnd.android.cursor.item/myProvider.data.text";
    public static final String CONTENT_TYPE_MULTIPLE = "vnd.android.cursor.dir/myProvider.data.text";
}