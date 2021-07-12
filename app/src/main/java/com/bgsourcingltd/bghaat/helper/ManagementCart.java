package com.bgsourcingltd.bghaat.helper;

import android.content.Context;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.models.NewArrivalModel;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;

    }

    public void insertFood(NewArrivalModel item){
        ArrayList<NewArrivalModel> listFood = getListCard();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getFoodTitle().equals(item.getFoodTitle())) {
                existAlready = true;
                n = i;
                break;
            }
        }

        if (existAlready){
            listFood.get(n).setNumberInCart(item.getNumberInCart());
        }
        else {
            listFood.add(item);
        }

        tinyDB.putListObject("CardList", listFood);
        Toast.makeText(context, "Added To Your Card", Toast.LENGTH_SHORT).show();

    }

    public ArrayList<NewArrivalModel> getListCard(){
        return tinyDB.getListObject("CardList");
    }
}
