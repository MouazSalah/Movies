package com.example.movieapp.RecyclerviewClasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.movieapp.MovieData.MovieResponce;
import com.example.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MovieHolder>
{
    OnMovieClickedInterface onMovieClickedInterface;

    String IMAGE_BASEURL = "http://image.tmdb.org/t/p/w500";

    List<MovieResponce.ResultsBean> moviesList;
    Context context;

    public RecyclerAdapter(Context c , List<MovieResponce.ResultsBean> moviesList)
    {
        this.context = c;
        this.moviesList = moviesList;
        Log.d("MOVIE constructor" , "" + moviesList.size());
    }
    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_format, viewGroup, false);
        return new MovieHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int i)
    {
        Log.d("viewholder binding", "binding method");

        for (int h = 0; h <moviesList.size(); h ++)
        {
            Log.d("MOVIE length of movies" , "" + moviesList.size());

            MovieResponce.ResultsBean resultsBean = moviesList.get(i);

            movieHolder.nameTextview.setText(resultsBean.getTitle());
            movieHolder.genreTextview.setText(resultsBean.getOverview());
            movieHolder.ratingtextview.setText("" + resultsBean.getVote_average());
            movieHolder.datetextview.setText("" + resultsBean.getRelease_date());

            Picasso.get().load(IMAGE_BASEURL + resultsBean.getPoster_path()).into(movieHolder.posterImageView);




        }
    }

    @Override
    public int getItemCount()
    {
        return moviesList.size();
    }


    class MovieHolder extends RecyclerView.ViewHolder
    {

        ImageView posterImageView;
        TextView nameTextview, ratingtextview, genreTextview, datetextview;

        public MovieHolder(@NonNull View itemView)
        {
            super(itemView);

            posterImageView = (ImageView) itemView.findViewById(R.id.movieposter_imageview);
            nameTextview = (TextView) itemView.findViewById(R.id.moviename_textview);
            ratingtextview = (TextView) itemView.findViewById(R.id.movierating_textview);
            genreTextview = (TextView) itemView.findViewById(R.id.moviegenre_textview);
            datetextview = (TextView) itemView.findViewById(R.id.item_movie_release_date);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int position = getAdapterPosition();
                    onMovieClickedInterface.onRecyclerItemClicked(moviesList.get(position));
                }
            });
        }
    }



    public interface OnMovieClickedInterface
    {
        public void onRecyclerItemClicked(MovieResponce.ResultsBean resultsBean);
    }

    public void OnMovieClicked(OnMovieClickedInterface onMovieClickedInterface)
    {
        this.onMovieClickedInterface = onMovieClickedInterface;

    }

}
