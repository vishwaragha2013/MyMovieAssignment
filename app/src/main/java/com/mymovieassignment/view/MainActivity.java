package com.mymovieassignment.view;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.mymovieassignment.R;
import com.mymovieassignment.di.component.DaggerAppComponent;
import com.mymovieassignment.viewmodel.LoginViewModel;


/**
 * MainActivity class for holding fragments
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private NavController mNavController;
    private LoginViewModel mViewModel;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeUI();
    }


    /**
     * method to initialize login UI
     */
    public void initializeUI() {
        DaggerAppComponent.create().inject(this);

        DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);

        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        mViewModel.getGoogleSignInClient().observe(this, googleSignInClient -> mGoogleSignInClient = googleSignInClient);

        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            mNavController.navigate(R.id.action_loginFragment_to_movieListFragment);
            Log.d(TAG, "Google Account:" + GoogleSignIn.getLastSignedInAccount(this).getDisplayName());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem menuItem = menu.findItem(R.id.action_logout);

        if (GoogleSignIn.getLastSignedInAccount(this) == null) {
            menuItem.setVisible(false);
        } else {
            menuItem.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_logout:
                signOut();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * method used to SignOut from google account
     */
    public void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    mNavController.navigate(R.id.loginFragment);
                    invalidateOptionsMenu();
                });
    }
}
