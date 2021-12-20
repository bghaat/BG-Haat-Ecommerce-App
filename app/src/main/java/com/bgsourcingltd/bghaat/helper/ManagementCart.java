package com.bgsourcingltd.bghaat.helper;

import android.content.Context;


import com.bgsourcingltd.bghaat.Interface.ChangeNumberItemsListener;
import com.bgsourcingltd.bghaat.cartcounter.CartCounter;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;


import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;
    private CartCounter cartCounter;


    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
        cartCounter = new CartCounter(context);

    }

    public void insertFood(NewArrivalModel item){
        //done for new arrival
        ArrayList<NewArrivalModel> listFood = getListCard();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getId().equals(item.getId())) {
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
            cartCounter.increaseCartValue();

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
        if (listfood.get(position).getNumberInCart() >= 2) {
            //Here check value is zero or not
            //listfood.remove(position);
            listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() - 1);

            //cartCounter.decreaseCartValue();

        }
        tinyDB.putListObject("CardList", listfood);
        changeNumberItemsListener.changed();
    }

    public void DeleteItem(ArrayList<NewArrivalModel> modelsItem, int position, ChangeNumberItemsListener changeNumberItemsListener){
        modelsItem.remove(position);

        tinyDB.putListObject("CardList", modelsItem);
        changeNumberItemsListener.changed();

        cartCounter.decreaseCartValue();
    }

    public Double getTotalFee(){
        ArrayList<NewArrivalModel> listFood2 = getListCard();
        double fee = 0;
        for (int i = 0; i < listFood2.size(); i++) {
            fee = fee + (Double.parseDouble(listFood2.get(i).getPrice()) * listFood2.get(i).getNumberInCart());
        }
        return fee;
    }
}
