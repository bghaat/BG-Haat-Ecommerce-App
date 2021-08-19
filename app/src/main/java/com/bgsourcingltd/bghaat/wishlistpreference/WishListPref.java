package com.bgsourcingltd.bghaat.wishlistpreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class WishListPref {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static final String WISH_LIST = "wish_list";


    public WishListPref(Context context){
        preferences = context.getSharedPreferences("wish_list",Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    private void setWishList(List<NewArrivalModel> modelList,String key){
        Gson gson = new Gson();
        String json = gson.toJson(modelList);
        editor.putString(key, json);
        editor.apply();

    }

    private ArrayList<NewArrivalModel> getWishList(String key){

        Gson gson = new Gson();
        String json = preferences.getString(key, null);
        Type type = new TypeToken<ArrayList<NewArrivalModel>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

}
