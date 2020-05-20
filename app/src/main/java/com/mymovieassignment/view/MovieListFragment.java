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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mymovieassignment.R;
import com.mymovieassignment.databinding.FragmentMovieListBinding;
import com.mymovieassignment.di.component.DaggerAppComponent;
import com.mymovieassignment.model.Movie;
import com.mymovieassignment.utils.Constants;
import com.mymovieassignment.utils.Utility;
import com.mymovieassignment.view.adapter.PopularMoviesAdapter;
import com.mymovieassignment.view.callback.OnMovieClickListener;
import com.mymovieassignment.viewmodel.MovieListViewModel;
import com.mymovieassignment.viewmodel.ViewModelFactory;

import javax.inject.Inject;


/**
 * Fragment class for movie details
 */
public class MovieListFragment extends Fragment implements OnMovieClickListener {

    private FragmentMovieListBinding mBinding;

    public static final String TAG = MovieListFragment.class.getSimpleName();

    @Inject
    PopularMoviesAdapter mMoviesAdapter;

    @Inject
    ViewModelFactory viewModelFactory;

    private NavController mNavController;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false);
        initializeUI();

        return mBinding.getRoot();
    }

    /**
     * method to initialize movie list UI
     */
    public void initializeUI() {

        DaggerAppComponent.create().inject(this);
        mBinding.setLifecycleOwner(this);

        if (!Utility.checkConnectivity(getActivity())) {
            Utility.showNetworkError(getActivity(), getActivity().findViewById(android.R.id.content));
        }

        MovieListViewModel moviesViewModel = new ViewModelProvider(this, viewModelFactory).get(MovieListViewModel.class);
        mBinding.setMovieListViewModel(moviesViewModel);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        RecyclerView rvMovies = mBinding.rvMovies;
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mMoviesAdapter.setMovieClickListener(this);
        rvMovies.setAdapter(mMoviesAdapter);

        mBinding.progressCircular.setVisibility(View.VISIBLE);
        moviesViewModel.getMoviesLiveData().observe(getActivity(), moviePagedList -> {
            mMoviesAdapter.submitList(moviePagedList);
            Log.d(TAG, "moviePagedList:" + moviePagedList);
            mBinding.progressCircular.setVisibility(View.GONE);
        });
    }


    @Override
    public void onMovieCardClicked(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.MOVIE_ID, movie.getId());
        mNavController.navigate(R.id.action_movieListFragment_to_movieDetailsFragment, bundle);
    }

}
