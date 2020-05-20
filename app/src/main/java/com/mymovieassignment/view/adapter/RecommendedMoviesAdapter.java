package com.mymovieassignment.view.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mymovieassignment.R;
import com.mymovieassignment.databinding.MovieCardItemBinding;
import com.mymovieassignment.model.Movie;

import java.util.ArrayList;

import javax.inject.Inject;


/**
 * Adapter class to set recommended movies to recycler view
 */
public class RecommendedMoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> {


    private MovieCardItemBinding movieCardItemBinding;
    private ArrayList<Movie> mMovieList;



    @Inject
    public RecommendedMoviesAdapter() {

    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        movieCardItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.movie_card_item, parent, false);
        return new MoviesViewHolder(movieCardItemBinding, null);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder moviesViewHolder, int position) {
        Movie movie = mMovieList.get(position);
        moviesViewHolder.movieCardItemBinding.setMovie(movie);
    }

    @Override
    public int getItemCount() {
        if (mMovieList != null) {
            return mMovieList.size();
        }
        return 0;
    }


    /**
     * method sets list of movies to adapter
     *
     * @param movieList
     */
    public void setMovieList(ArrayList<Movie> movieList) {
        this.mMovieList = movieList;
        notifyDataSetChanged();
    }

}
