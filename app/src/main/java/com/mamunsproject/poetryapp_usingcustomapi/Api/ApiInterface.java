package com.mamunsproject.poetryapp_usingcustomapi.Api;

import com.mamunsproject.poetryapp_usingcustomapi.Response.DeleteResponse;
import com.mamunsproject.poetryapp_usingcustomapi.Response.GetPoetryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    // GET r modde Api r end point declare korte hoy
    @GET("getPoetry.php")

    //Jemon Data hobe thik sei type r akta java class create korte hobe
    Call<GetPoetryResponse> getPoetry();


    //end Point
    @FormUrlEncoded
    @POST("deletepoetry.php")
    //Request Parameter
    Call<DeleteResponse> deletePoetry(@Field("poetry_id") String poetry_id);


    //Jodi From Data r Maddome Data pass kori tahole  @FormUrlEncoded bosano lagbe
    @FormUrlEncoded
    @POST("addPoetry.php")
    Call<DeleteResponse> addPoetry(@Field("poetry2") String addPoetryData, @Field("poet_name") String addPoetName);


    @FormUrlEncoded
    @POST("updatepoetry.php")
    Call<DeleteResponse> updatePoetry(@Field("poetry_data") String poetryData, @Field("id") String id);

}
