package com.mymovieassignment.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.mymovieassignment.model.Movie;
import com.mymovieassignment.model.MoviesResponse;
import com.mymovieassignment.services.MoviesApiInterface;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * ViewModel class to store and populate the  movie details.
 */
public class MovieDetailsViewModel extends ViewModel {


    private CompositeDisposable disposable;
    private MutableLiveData<Movie> mutableLiveData = new MutableLiveData<>();

    private MutableLiveData<ArrayList<Movie>> mutableMoviesLiveData = new MutableLiveData<>();

    private final String TAG = MovieDetailsViewModel.class.getName();

    @Inject
    MoviesApiInterface mService;

    @Inject
    public MovieDetailsViewModel() {
        disposable = new CompositeDisposable();
    }

    /**
     * method returns LiveData object which contains Movie object
     *
     * @return mutableLiveData
     */
    public MutableLiveData<Movie> getMovieDetailsLiveData() {
        return mutableLiveData;
    }

    /**
     * method returns LiveData object which contains Movie list object
     *
     * @return mutableMoviesLiveData
     */
    public MutableLiveData<ArrayList<Movie>> getMovieRecommendedMovieLiveData() {
        return mutableMoviesLiveData;
    }


    /**
     * Method to get the movie details based on movie id from movie db server.
     *
     * @param movieId
     */
    public void getMovieDetails(int movieId) {

        disposable.add(mService.getMovieDetails(movieId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Movie>() {
                    @Override
                    public void onSuccess(Movie movie) {
                        mutableLiveData.postValue(movie);
                        Log.d(TAG, "Movie details name:" + movie.getTitle());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }


    /**
     * Method to get the recommended movie list based on movie id from movie db server.
     *
     * @param movieId
     */
    public void getRecommendedMovies(int movieId) {
        disposable.add(mService.getRecommendedMovies(movieId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MoviesResponse>() {
                    @Override
                    public void onSuccess(MoviesResponse moviesResponse) {
                        mutableMoviesLiveData.postValue(moviesResponse.getMovieResults());
                        Log.d(TAG, "RecommendedMovies size:" + moviesResponse.getMovieResults().size());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
