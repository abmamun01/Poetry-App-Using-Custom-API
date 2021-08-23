package com.mamunsproject.poetryapp_usingcustomapi.Api;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static Retrofit RETROFIT = null;

    public static Retrofit getClient() {

        //Jate bar bar Retrofit create na hoy ak bar e jate hoy
        if (RETROFIT == null) {

            //For generating request
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();


            Gson gson = new GsonBuilder().create();


            RETROFIT = new Retrofit.Builder().baseUrl("http://192.168.0.15/poetryapis/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();


        }
        return RETROFIT;
    }

}
