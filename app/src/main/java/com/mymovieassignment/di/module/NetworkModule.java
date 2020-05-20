package com.mymovieassignment.di.module;

import com.mymovieassignment.services.MoviesApiInterface;
import com.mymovieassignment.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * class helps to construct the RetrofitClient,OkHttpClient,MoviesApiInterface objects which will be provided as dependencies
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    Retrofit provideRetrofitClient(OkHttpClient.Builder okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient.Builder provideOkHttpClient() {
        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl httpUrl = request.url();
                HttpUrl url = httpUrl.newBuilder().addQueryParameter(Constants.API_KEY_TEXT, Constants.API_KEY).build();
                Request.Builder urlBuilder = request.newBuilder().url(url);
                Request urlRequest = urlBuilder.build();
                return chain.proceed(urlRequest);
            }
        });
    }


    @Provides
    @Singleton
    MoviesApiInterface provideMoviesService(Retrofit retrofit) {
        return retrofit.create(MoviesApiInterface.class);
    }


}
