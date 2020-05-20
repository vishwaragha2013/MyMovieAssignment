package com.mymovieassignment.di.module;


import androidx.lifecycle.ViewModel;

import com.mymovieassignment.viewmodel.LoginViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * class helps to construct the LoginViewModel object which will be provided as dependency
 */
@Module
public abstract class LoginViewModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel provideLoginViewModel(LoginViewModel loginViewModel);
}
