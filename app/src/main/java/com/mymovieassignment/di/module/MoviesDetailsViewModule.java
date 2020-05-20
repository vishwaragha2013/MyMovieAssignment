package com.mymovieassignment.di.module;


import androidx.lifecycle.ViewModel;
import com.mymovieassignment.viewmodel.MovieDetailsViewModel;


import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


/**
 * class helps to construct the MovieDetailsViewModel object which will be provided as dependency
 */
@Module
public abstract class MoviesDetailsViewModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel.class)
    abstract ViewModel provideMovieDetailsViewModel(MovieDetailsViewModel movieDetailsViewModel);
}
