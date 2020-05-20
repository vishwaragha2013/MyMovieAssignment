package com.mymovieassignment.view.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mymovieassignment.databinding.MovieCardItemBinding;
import com.mymovieassignment.model.Movie;
import com.mymovieassignment.view.callback.OnMovieClickListener;

/**
 * Class used to hold movies views
 */
public class MoviesViewHolder extends RecyclerView.ViewHolder {


    public MovieCardItemBinding movieCardItemBinding;
    private final String TAG = MoviesViewHolder.class.getName();

    public MoviesViewHolder(@NonNull final MovieCardItemBinding movieCardItemBinding, OnMovieClickListener movieClickListener) {
        super(movieCardItemBinding.getRoot());
        this.movieCardItemBinding = movieCardItemBinding;

        movieCardItemBinding.ivPoster.setOnClickListener(v -> {
            Movie movie = movieCardItemBinding.getMovie();
            Log.i(TAG, "Movie name:" + movie.getTitle());
            if (movieClickListener != null) {
                movieClickListener.onMovieCardClicked(movie);
            }
        });
    }
}