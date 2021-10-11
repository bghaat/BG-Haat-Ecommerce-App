package com.bgsourcingltd.bghaat.Interface;

import com.bgsourcingltd.bghaat.models.NewArrivalModel;

import java.util.List;

public interface DeleteWishListListener {
    void deleteItem(List<NewArrivalModel> list, int position, NewArrivalModel model);
}
