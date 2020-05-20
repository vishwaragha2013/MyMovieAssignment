package com.mymovieassignment.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.mymovieassignment.R;
import com.mymovieassignment.databinding.MovieCardItemBinding;
import com.mymovieassignment.model.Movie;
import com.mymovieassignment.view.callback.OnMovieClickListener;

import javax.inject.Inject;


/**
 * Adapter class to set popular movies to recycler view
 */
public class PopularMoviesAdapter extends PagedListAdapter<Movie, MoviesViewHolder> {


    private MovieCardItemBinding mMovieCardItemBinding;
    private OnMovieClickListener movieClickListener;


    /**
     * DiffUtil is a utility that calculates the difference between two lists and outputs a
     * list of update operations that converts the first list into the second one.
     */
    private static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Inject
    public PopularMoviesAdapter() {
        super(PopularMoviesAdapter.DIFF_CALLBACK);

    }

    /**
     * method sets movies onclick listener
     *
     * @param onMovieClickListener
     */
    public void setMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.movieClickListener = onMovieClickListener;
    }


    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mMovieCardItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.movie_card_item, parent, false);
        return new MoviesViewHolder(mMovieCardItemBinding, movieClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder moviesViewHolder, int position) {
        moviesViewHolder.movieCardItemBinding.setMovie(getItem(position));
    }

}
