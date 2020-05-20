package com.mymovieassignment.viewmodel;



import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.mymovieassignment.MovieApplication;
import com.mymovieassignment.di.component.DaggerAppComponent;

import javax.inject.Inject;

/**
 * ViewModel class to store and populate GoogleSignInClient object.
 */
public class LoginViewModel extends ViewModel {


    private final MutableLiveData<GoogleSignInClient> mGoogleSignInClient = new MutableLiveData<GoogleSignInClient>();

    @Inject
    GoogleSignInOptions gso;


    @Inject
    public LoginViewModel() {

        DaggerAppComponent.create().inject(this);

        mGoogleSignInClient.postValue(GoogleSignIn.getClient(MovieApplication.getContext(), gso));

    }

    /**
     * method to get GoogleSignInClient
     * @return mGoogleSignInClient
     */
    public MutableLiveData<GoogleSignInClient> getGoogleSignInClient() {
        return mGoogleSignInClient;
    }


}
