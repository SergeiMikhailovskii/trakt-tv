package com.mikhailovskii.trakttv.data.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieAPIFactory {

    private static MovieAPIFactory mInstance;
    private final String BASE_URL = "https://api.trakt.tv";

    private Retrofit mRetrofit;

    private MovieAPIFactory() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request requestBuilder = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("trakt-api-version", "2")
                    .header("trakt-api-key", "a92265d647322f33c64824cbd53366ad7fe29c8f80834b770d299405ca04801b")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(requestBuilder);
        });

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static MovieAPIFactory getInstance() {
        if (mInstance == null) {
            mInstance = new MovieAPIFactory();
        }
        return mInstance;
    }

    public MovieAPI getAPIService() {
        return mRetrofit.create(MovieAPI.class);
    }

}
