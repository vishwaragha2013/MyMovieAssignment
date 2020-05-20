package com.mymovieassignment.data;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.mymovieassignment.di.component.DaggerAppComponent;
import com.mymovieassignment.model.Movie;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Factory class for movie data source
 */
public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    private MutableLiveData<MovieDataSource> mMutableLiveData;

    @Inject
    MovieDataSource mMovieDataSource;

    CompositeDisposable mCompositeDisposable;


    @Inject
    public MovieDataSourceFactory(CompositeDisposable compositeDisposable) {
        DaggerAppComponent.create().inject(this);
        mMutableLiveData = new MutableLiveData<>();
        mCompositeDisposable = compositeDisposable;
    }

    @NonNull
    @Override
    public DataSource<Integer, Movie> create() {

        mMutableLiveData = new MutableLiveData<>();
        mMutableLiveData.postValue(mMovieDataSource);

        return mMovieDataSource;
    }
}
