package com.bgsourcingltd.bghaat.wishlistpreference;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class WishListPref {
    public static final String PREFS_NAME = "WORD_APP";
    public static final String FAVORITES = "Words_Favorite";


    public WishListPref(){

    }

    //save to sharedPreference
    public void saveFavorites(Context context, List<NewArrivalModel> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);
        editor.commit();
    }

    //add to sharedPreference
    public void addFavorite(Context context, NewArrivalModel word) {
        ArrayList<NewArrivalModel> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<NewArrivalModel>();
        favorites.add(word);
        saveFavorites(context, favorites);
    }


    public void removeFavorite(Context context) {
        /*ArrayList<NewArrivalModel> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(word);
            saveFavorites(context, favorites);
        }*/

        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();

    }

    //get all newArrival from sharedPreference
    public ArrayList<NewArrivalModel> getFavorites(Context context) {
        SharedPreferences settings;
        List<NewArrivalModel> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            NewArrivalModel[] favoriteItems = gson.fromJson(jsonFavorites,	NewArrivalModel[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<NewArrivalModel>(favorites);
        } else
            return null;

        return (ArrayList<NewArrivalModel>) favorites;
    }
}
