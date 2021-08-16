package com.bgsourcingltd.bghaat.cartcounter;

import android.content.Context;
import android.content.SharedPreferences;

public class CartCounter {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String CART_STATUS = "cart_status";

    public CartCounter(Context context){
        sharedPreferences = context.getSharedPreferences("cart",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public int getCartValue(){
        return sharedPreferences.getInt(CART_STATUS,0);

    }

    public void increaseCartValue(){
        int cartNumber = getCartValue()+1;
        editor.putInt(CART_STATUS,cartNumber);
        editor.commit();
    }

    public void decreaseCartValue(){
        int cartNumber = getCartValue()-1;
        editor.putInt(CART_STATUS,cartNumber);
        editor.commit();
    }

}
