package com.bgsourcingltd.bghaat.userauth;

import android.content.Context;
import android.content.SharedPreferences;

public class CouponAuth {
    private static String COUPON_STATUS = "coupon";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public CouponAuth(Context context){
        sharedPreferences = context.getSharedPreferences("coupon_validity",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void setCouponValue(boolean couponStatus){
        editor.putBoolean(COUPON_STATUS,couponStatus);
        editor.commit();

    }

    public boolean getCouponValue(){
        return sharedPreferences.getBoolean(COUPON_STATUS,true);
    }
}
