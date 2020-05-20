package com.mymovieassignment.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.mymovieassignment.data.MovieDataSourceFactory;
import com.mymovieassignment.di.component.DaggerAppComponent;
import com.mymovieassignment.model.Movie;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * ViewModel class to store and populate the  movie popular movie list.
 */
public class MovieListViewModel extends ViewModel {


    private LiveData<PagedList<Movie>> mMoviesPagedList;
    private final String TAG = MovieListViewModel.class.getName();

    @Inject
    CompositeDisposable disposable;

    @Inject
    MovieDataSourceFactory movieDataSourceFactory;


    @Inject
    public MovieListViewModel() {
        initialize();
    }


    /**
     * method to initialize movie paged list
     */
    public void initialize() {
        Log.d(TAG, "initialize");
        Executor executor = Executors.newFixedThreadPool(5);

        DaggerAppComponent.create().inject(this);

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(50)
                .build();

        mMoviesPagedList = new LivePagedListBuilder<>(movieDataSourceFactory, config)
                .setFetchExecutor(executor)
                .build();

    }

    /**
     * method to get movies paged list
     *
     * @return moviesPagedList
     */
    public LiveData<PagedList<Movie>> getMoviesLiveData() {
        Log.d(TAG, "mMoviesPagedList size:" + mMoviesPagedList);
        return mMoviesPagedList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
        Log.d(TAG, "CompositeDisposable dispose");
    }

}


