package com.example.moviewapplication.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofitclient {
    private static String BASE_URL = "http://api.themoviedb.org/3/search/";
    private static Retrofitclient mInstatnace;
    private Retrofit retrofit;

    public Retrofitclient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized  Retrofitclient getmInstatnace()
    {
        if (mInstatnace==null)
        {
            mInstatnace = new Retrofitclient();
        }
        return mInstatnace;
    }

    public Apiinterface getApi()
    {
        return retrofit.create(Apiinterface.class);
    }


}
