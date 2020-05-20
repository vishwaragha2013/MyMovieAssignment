package com.mymovieassignment.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * class helps to construct the CompositeDisposable object which will be provided as dependency
 */
@Module
public class CompositeDisposableModule {

    @Provides
    @Singleton
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }

}
