package com.bgsourcingltd.bghaat.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bgsourcingltd.bghaat.Interface.ChangeNumberItemsListener;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;


    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);

    }

    public void insertFood(NewArrivalModel item){
        //done for new arrival
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

    }

    public ArrayList<NewArrivalModel> getListCard(){
        return tinyDB.getListObject("CardList");
    }

    public void plusNumberFood(ArrayList<NewArrivalModel> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener){
        listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CardList", listfood);
        changeNumberItemsListener.changed();
    }

    public void MinusNumberFood(ArrayList<NewArrivalModel> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener){
        if (listfood.get(position).getNumberInCart() == 1) {
            //Here check value is zero or not
            listfood.remove(position);
        } else {
            listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() - 1);
        }
        tinyDB.putListObject("CardList", listfood);
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee(){
        ArrayList<NewArrivalModel> listFood2 = getListCard();
        double fee = 0;
        for (int i = 0; i < listFood2.size(); i++) {
            fee = fee + (listFood2.get(i).getPrice() * listFood2.get(i).getNumberInCart());
        }
        return fee;
    }
}
