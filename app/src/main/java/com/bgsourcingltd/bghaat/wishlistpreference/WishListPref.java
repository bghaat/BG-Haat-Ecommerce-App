package com.bgsourcingltd.bghaat.wishlistpreference;


import android.content.Context;
import android.content.SharedPreferences;

import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class WishListPref {
    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";


    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<NewArrivalModel> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, NewArrivalModel product) {
        ArrayList<NewArrivalModel> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<NewArrivalModel>();
        favorites.add(product);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, NewArrivalModel product) {
        ArrayList<NewArrivalModel> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(product);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<NewArrivalModel> getFavorites(Context context) {
        SharedPreferences settings;
        List<NewArrivalModel> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            NewArrivalModel[] favoriteItems = gson.fromJson(jsonFavorites,
                    NewArrivalModel[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<NewArrivalModel>(favorites);
        } else
            return null;

        return (ArrayList<NewArrivalModel>) favorites;
    }
}
