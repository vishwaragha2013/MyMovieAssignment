package com.mymovieassignment.services;

import com.mymovieassignment.model.Movie;
import com.mymovieassignment.model.MoviesResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * interface provides movie APIs
 */
public interface MoviesApiInterface  {

    @GET("movie/popular")
    Single<MoviesResponse> getMovies(@Query("page") int page);

    @GET("movie/{movie_id}")
    Single<Movie> getMovieDetails(@Path("movie_id") int id);

    @GET("movie/{movie_id}/recommendations")
    Single<MoviesResponse> getRecommendedMovies(@Path("movie_id") int id);
}
