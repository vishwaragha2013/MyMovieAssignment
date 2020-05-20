package com.mymovieassignment.di.module;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * class helps to construct the GoogleSignInOptions object which will be provided as dependency
 */
@Module
public class GoogleSignInModule {

    @Provides
    @Singleton
    GoogleSignInOptions provideGoogleSignInOptions(){
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }



}
