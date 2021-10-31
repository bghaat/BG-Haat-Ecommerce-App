package com.bgsourcingltd.bghaat.userauth;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPhoneAuth {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String LOGIN_STATUS = "phone";

    public UserPhoneAuth(Context context){
        sharedPreferences = context.getSharedPreferences("user_auth",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setPhoneNumber(String phoneNumber){
        editor.putString(LOGIN_STATUS,phoneNumber);
        editor.commit();
    }

    public String getPhoneNumber(){
        return sharedPreferences.getString(LOGIN_STATUS,"no data");
    }
}
