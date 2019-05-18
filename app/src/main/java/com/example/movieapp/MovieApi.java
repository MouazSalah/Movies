package com.example.movieapp;

import com.example.movieapp.MovieData.MovieResponce;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi
{

    @GET("movie/popular")
    Call<MovieResponce> getMovies (@Query("api_key") String apikey);

}
