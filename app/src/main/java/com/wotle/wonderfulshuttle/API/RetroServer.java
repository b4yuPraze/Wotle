package com.wotle.wonderfulshuttle.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
    private static final String baseURL = "http://9fcd-2404-c0-2910-00-38ec-8f80.ngrok.io/wotle/";
    private static Retrofit retro;

    public static Retrofit konekRetrofit(){
        if (retro == null){
            retro = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retro;
    }
}
