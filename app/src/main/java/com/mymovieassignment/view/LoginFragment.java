package com.mymovieassignment.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.mymovieassignment.R;
import com.mymovieassignment.databinding.FragmentLoginBinding;
import com.mymovieassignment.di.component.DaggerAppComponent;
import com.mymovieassignment.utils.Utility;
import com.mymovieassignment.viewmodel.LoginViewModel;
import com.mymovieassignment.viewmodel.ViewModelFactory;

import javax.inject.Inject;

/**
 * Fragment class for Login
 */
public class LoginFragment extends Fragment {

    private static final int RC_SIGN_IN = 1;
    private static final String TAG = LoginFragment.class.getSimpleName();

    private FragmentLoginBinding mBinding;

    public LoginViewModel mViewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    private GoogleSignInClient mGoogleSignInClient;

    private NavController mNavController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerAppComponent.create().inject(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        initializeUI();
        return mBinding.getRoot();
    }


    /**
     * method to initialize login UI
     */
    public void initializeUI() {
        mBinding.setLifecycleOwner(this);

        mViewModel = new ViewModelProvider(this, viewModelFactory).get(LoginViewModel.class);

        mBinding.setLoginViewModel(mViewModel);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        mViewModel.getGoogleSignInClient().observe(getActivity(), googleSignInClient -> mGoogleSignInClient = googleSignInClient);

        mBinding.btnLogin.setOnClickListener(v -> logIn());

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        getActivity().finish();
                    }
                });
    }

    /**
     * method used to login Google Account
     */
    private void logIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            processAccount(task);
        }
    }

    /**
     * method used to process on login completion
     *
     * @param task
     */
    private void processAccount(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            if (account != null) {
                mNavController.navigate(R.id.action_loginFragment_to_movieListFragment);
                getActivity().invalidateOptionsMenu();
                Log.d(TAG, "Google Account login success:" + account.getDisplayName());
            }
        } catch (ApiException e) {
            Log.d(TAG, "Google Account login failed:" + e.getStatusCode());
            if (!Utility.checkConnectivity(getActivity())) {
                Utility.showNetworkError(getActivity(), getActivity().findViewById(android.R.id.content));
            }
        }
    }
}
