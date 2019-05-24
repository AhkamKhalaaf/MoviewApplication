package com.example.moviewapplication.api;

import com.example.moviewapplication.models.Apiresponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Apiinterface {

    @GET("movie")
    Call<Apiresponse>getDatamovies(@Query("api_key")String api_key ,@Query("query") String query ,@Query("page") int page);
}
