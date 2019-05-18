package com.example.movieapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.movieapp.MovieData.MovieResponce;
import com.example.movieapp.RecyclerviewClasses.RecyclerAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{

    RecyclerView movieRecyclerview;
    RecyclerAdapter recyclerAdapter;
    List<MovieResponce.ResultsBean> resultsBeans = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getData();

        movieRecyclerview = (RecyclerView) findViewById(R.id.movies_recyclerview);

        recyclerAdapter = new RecyclerAdapter(this, resultsBeans);

        movieRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        movieRecyclerview.setAdapter(recyclerAdapter);


        recyclerAdapter.OnMovieClicked(new RecyclerAdapter.OnMovieClickedInterface()
        {
            @Override
            public void onRecyclerItemClicked(MovieResponce.ResultsBean resultsBean)
            {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                startActivity(intent);
            }
        });



    }

    private void getData()
    {
        String BASE_URL = "https://api.themoviedb.org/3/";

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                    .addInterceptor(httpLoggingInterceptor)
                                    .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        MovieApi movieApi = retrofit.create(MovieApi.class);

        Call<MovieResponce> getMovies = movieApi.getMovies( "b10c0cb5979d5830391dbe6bd3b71c4f" );
        getMovies.enqueue(new Callback<MovieResponce>()
        {
            @Override
            public void onResponse(Call<MovieResponce> call, Response<MovieResponce> response)
            {
                if (!response.isSuccessful())
                {
                    Log.d("MOVIE NOT SUCCESSFULLY" , "NOT CONNECTED");
                }
                else
                {
                    MovieResponce movieResponce = response.body();

                    for (int l =0 ; l<movieResponce.getResults().size(); l++ )
                    {
                         resultsBeans.add(movieResponce.getResults().get(l));

                        Log.d(" MOVIE title" , "title:" + movieResponce.getResults().get(l).getVote_average());
                    }

                    Log.d("MOVIE size of list" , "" + resultsBeans.size());
                }
            }
            @Override
            public void onFailure(Call<MovieResponce> call, Throwable t)
            {
                Log.d("MOVIE Failed" , t.getMessage());
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_popular)
        {
            return true;
        }
        if (id == R.id.action_toprated)
        {
            return true;
        }
        if (id == R.id.action_Upcoming)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
