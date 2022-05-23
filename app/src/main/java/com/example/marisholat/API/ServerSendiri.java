package com.example.marisholat.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerSendiri {
    private static String baseURL = "http://192.168.43.163/ysholat/";
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
