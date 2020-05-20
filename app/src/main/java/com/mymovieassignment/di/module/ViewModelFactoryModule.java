package com.mymovieassignment.di.module;

import androidx.lifecycle.ViewModelProvider;

import com.mymovieassignment.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;

/**
 * class helps to construct the ViewModelFactory object which will be provided as dependency
 */
@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory viewModelFactory);
}
