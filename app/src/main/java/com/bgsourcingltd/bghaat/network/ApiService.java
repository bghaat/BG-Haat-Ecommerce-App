package com.bgsourcingltd.bghaat.network;

import com.bgsourcingltd.bghaat.models.NewArrivalModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("catproduct/?cat=womens-fashion")
    Call<List<NewArrivalModel>> getWomenProduct();

    @GET("catproduct/?cat=mens-fashion")
    Call<List<NewArrivalModel>> getGentsProduct();

    @GET("catproduct/?cat=groceries-food")
    Call<List<NewArrivalModel>> getGroceryProduct();
}
