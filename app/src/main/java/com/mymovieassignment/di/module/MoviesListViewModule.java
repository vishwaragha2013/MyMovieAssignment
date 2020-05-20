package com.mymovieassignment.di.module;


import androidx.lifecycle.ViewModel;


import com.mymovieassignment.viewmodel.MovieListViewModel;


import dagger.Binds;
import dagger.Module;

import dagger.multibindings.IntoMap;

/**
 * class helps to construct the MovieListViewModel object which will be provided as dependency
 */
@Module
public abstract class MoviesListViewModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel.class)
    abstract ViewModel provideMovieListViewModel(MovieListViewModel movieListViewModel);

}
