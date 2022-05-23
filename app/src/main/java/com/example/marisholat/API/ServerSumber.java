package com.example.marisholat.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerSumber {
    private static String baseURL = "http://api.myquran.com/v1/sholat/";
    private static Retrofit retrofit;

    public static Retrofit konekRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
