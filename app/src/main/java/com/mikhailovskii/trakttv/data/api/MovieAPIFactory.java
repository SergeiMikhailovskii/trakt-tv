package com.mikhailovskii.trakttv.data.api;

import androidx.annotation.NonNull;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieAPIFactory {

    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String API_VERSION_HEADER = "trakt-api-version";
    private static final String API_KEY_HEADER = "trakt-api-key";

    private static final String CONTENT_TYPE_VALUE = "application/json";
    private static final String API_VERSION_VALUE = "2";
    private static final String API_KEY_VALUE = "a92265d647322f33c64824cbd53366ad7fe29c8f80834b770d299405ca04801b";

    private static final String BASE_URL = "https://api.trakt.tv";

    private static MovieAPIFactory mInstance;

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
                    .header(CONTENT_TYPE_HEADER, CONTENT_TYPE_VALUE)
                    .header(API_VERSION_HEADER, API_VERSION_VALUE)
                    .header(API_KEY_HEADER, API_KEY_VALUE)
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(requestBuilder);
        });

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @NonNull
    public static MovieAPIFactory getInstance() {
        if (mInstance == null) {
            mInstance = new MovieAPIFactory();
        }
        return mInstance;
    }

    @NonNull
    public MovieAPI getAPIService() {
        return mRetrofit.create(MovieAPI.class);
    }

}
