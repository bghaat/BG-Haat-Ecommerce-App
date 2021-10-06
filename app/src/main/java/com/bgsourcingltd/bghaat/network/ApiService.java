package com.bgsourcingltd.bghaat.network;

import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bgsourcingltd.bghaat.models.OrderResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {


    @GET("catproduct/?cat=womens-fashion")
    Call<List<NewArrivalModel>> getWomenProduct();

    @GET("catproduct/?cat=mens-fashion")
    Call<List<NewArrivalModel>> getGentsProduct();

    @GET("catproduct/?cat=groceries-food")
    Call<List<NewArrivalModel>> getGroceryProduct();

    @GET("catproduct/?cat=electronic")
    Call<List<NewArrivalModel>> getElectronicsProduct();

    @GET("catproduct/?cat=health-beauty")
    Call<List<NewArrivalModel>> getHeathBeauty();

    @GET("allproductapi")
    Call<List<NewArrivalModel>> getAllProduct();

    @GET("catproduct/?cat=kids-fashion")
    Call<List<NewArrivalModel>> getKidsProduct();

    @FormUrlEncoded
    @POST("postdatas/")
    Call<OrderResponse> postOrder(
            @Field("username") String username,
            @Field("phone") String phone,
            @Field("homeaddress") String homeaddress,
            @Field("orderlist") String orderlist

    );

}
