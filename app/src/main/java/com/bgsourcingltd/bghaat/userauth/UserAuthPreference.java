package com.bgsourcingltd.bghaat.userauth;

import android.content.Context;
import android.content.SharedPreferences;

public class UserAuthPreference {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String LOGIN_STATUS = "status";


    public UserAuthPreference(Context context){
        sharedPreferences = context.getSharedPreferences("user_auth",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoginStatus(boolean status){
        editor.putBoolean(LOGIN_STATUS,status);
        editor.commit();
    }

    public boolean getLoginStatus(){
        return sharedPreferences.getBoolean(LOGIN_STATUS,false);

    }


}
