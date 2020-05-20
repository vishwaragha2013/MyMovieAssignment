package com.mymovieassignment.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mymovieassignment.R;
import com.mymovieassignment.databinding.FragmentMovieDetailsBinding;
import com.mymovieassignment.di.component.DaggerAppComponent;
import com.mymovieassignment.utils.Constants;
import com.mymovieassignment.utils.Utility;
import com.mymovieassignment.view.adapter.RecommendedMoviesAdapter;
import com.mymovieassignment.viewmodel.MovieDetailsViewModel;
import com.mymovieassignment.viewmodel.ViewModelFactory;

import javax.inject.Inject;

/**
 * Fragment class for movie details
 */
public class MovieDetailsFragment extends Fragment {

    public static final String TAG = MovieDetailsFragment.class.getSimpleName();
    private FragmentMovieDetailsBinding mBinding;
    private MovieDetailsViewModel mMovieDetailsViewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    @Inject
    RecommendedMoviesAdapter moviesAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false);
        initializeUI();
        return mBinding.getRoot();
    }


    /**
     * method to initialize movie detail UI
     */
    public void initializeUI() {
        DaggerAppComponent.create().inject(this);
        mMovieDetailsViewModel = new ViewModelProvider(this, viewModelFactory).get(MovieDetailsViewModel.class);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int movieId = bundle.getInt(Constants.MOVIE_ID, 0);
            mMovieDetailsViewModel.getMovieDetails(movieId);
            mMovieDetailsViewModel.getRecommendedMovies(movieId);
        }


        mBinding.setLifecycleOwner(this);

        if (!Utility.checkConnectivity(getActivity())) {
            Utility.showNetworkError(getActivity(), getActivity().findViewById(android.R.id.content));
        }

        mBinding.progressCircular.setVisibility(View.VISIBLE);

        mMovieDetailsViewModel.getMovieDetailsLiveData().observe(getActivity(), movie -> {
            mBinding.setMovie(movie);
            Log.i(TAG, "Movie details:" + movie.getBudget());
        });

        RecyclerView rvMovies = mBinding.rvRecommendedMovies;
        rvMovies.setHasFixedSize(true);

        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvMovies.setAdapter(moviesAdapter);

        mMovieDetailsViewModel.getMovieRecommendedMovieLiveData().observe(getActivity(), movies -> {
            moviesAdapter.setMovieList(movies);
            mBinding.progressCircular.setVisibility(View.GONE);
            Log.d(TAG, "Recommended movies:" + movies);
        });

    }


}
