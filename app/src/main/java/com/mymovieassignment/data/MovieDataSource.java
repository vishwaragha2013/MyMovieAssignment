package com.mymovieassignment.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.mymovieassignment.di.component.DaggerAppComponent;
import com.mymovieassignment.di.module.NetworkModule;
import com.mymovieassignment.model.Movie;
import com.mymovieassignment.model.MoviesResponse;
import com.mymovieassignment.services.MoviesApiInterface;
import com.mymovieassignment.utils.Constants;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * class helps to load the movie list on scrolling recyclerview
 */
public class MovieDataSource extends PageKeyedDataSource<Integer, Movie> {


    private static final String TAG = MovieDataSource.class.getSimpleName();


    CompositeDisposable mCompositeDisposable;

    @Inject
    MoviesApiInterface mService;


    @Inject
    public MovieDataSource(CompositeDisposable compositeDisposable) {
        DaggerAppComponent.builder().networkModule(new NetworkModule()).build().inject(this);
        mCompositeDisposable = compositeDisposable;
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Movie> callback) {

        mCompositeDisposable.add(mService.getMovies(Constants.FIRST_PAGE).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MoviesResponse>() {
                    @Override
                    public void onSuccess(MoviesResponse moviesResponse) {
                        callback.onResult(moviesResponse.getMovieResults(),
                                Constants.PREVIOUS_PAGE, Constants.NEXT_PAGE);
                        Log.d(TAG, "Movies result size:" + moviesResponse.getMovieResults().size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error while fetching movies:" + e.getMessage());
                    }
                }));

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie> callback) {

        final int currentPage = params.key;

        mCompositeDisposable.add(mService.getMovies(currentPage).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MoviesResponse>() {
                    @Override
                    public void onSuccess(MoviesResponse moviesResponse) {
                        int nextKey = currentPage + 1;
                        callback.onResult(moviesResponse.getMovieResults(),
                                nextKey);
                        Log.d(TAG, "Movies result size:" + "Page:" + nextKey + "::" + moviesResponse.getMovieResults().size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error while fetching the movies:" + e.getMessage());
                    }
                }));
    }
}
