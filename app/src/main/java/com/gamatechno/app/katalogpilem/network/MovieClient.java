package com.gamatechno.app.katalogpilem.network;

import com.gamatechno.app.katalogpilem.model.OtherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieClient {

    @GET("/3/search/movie")
    Call<OtherData> searchMovie(@Query("api_key") String api_key,
                                @Query("language") String language,
                                @Query("query") String query,
                                @Query("page") String page,
                                @Query("include_adult") String include_adult);

    @GET("3/movie/now_playing")
    Call<OtherData> getNowPlaying(@Query("api_key") String api_key,
                                  @Query("language") String language);

    @GET("3/movie/upcoming")
    Call<OtherData> getUpcoming(@Query("api_key") String api_key,
                                @Query("language") String language);

}

