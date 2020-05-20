package com.mymovieassignment.di.component;


import com.mymovieassignment.data.MovieDataSource;
import com.mymovieassignment.data.MovieDataSourceFactory;
import com.mymovieassignment.di.module.CompositeDisposableModule;
import com.mymovieassignment.di.module.GoogleSignInModule;
import com.mymovieassignment.di.module.LoginViewModule;
import com.mymovieassignment.di.module.MoviesDetailsViewModule;
import com.mymovieassignment.di.module.MoviesListViewModule;
import com.mymovieassignment.di.module.NetworkModule;
import com.mymovieassignment.di.module.ViewModelFactoryModule;
import com.mymovieassignment.view.LoginFragment;
import com.mymovieassignment.view.MainActivity;
import com.mymovieassignment.view.MovieDetailsFragment;
import com.mymovieassignment.view.MovieListFragment;
import com.mymovieassignment.viewmodel.LoginViewModel;
import com.mymovieassignment.viewmodel.MovieListViewModel;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Singleton interface which acts as a bridge between @Module and @Inject
 */
@Singleton
@Component(modules = {NetworkModule.class, MoviesListViewModule.class, ViewModelFactoryModule.class, MoviesDetailsViewModule.class, LoginViewModule.class, CompositeDisposableModule.class, GoogleSignInModule.class})
public interface AppComponent {

    void inject(MovieListFragment movieListFragment);

    void inject(MovieDetailsFragment movieDetailsFragment);

    void inject(LoginFragment loginFragment);

    void inject(MovieDataSource movieDataSource);

    void inject(MainActivity mainActivity);

    void inject(MovieDataSourceFactory movieDataSourceFactory);

    void inject(MovieListViewModel movieListViewModel);

    void inject(LoginViewModel loginViewModel);

}
