package com.mikhailovskii.trakttv.data.model;

import com.mikhailovskii.trakttv.ui.movies_list.APIService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static NetworkService mInstance;
    private static final String baseUrl = "https://api.trakt.tv/";
    private Retrofit mRetrofit;

    private NetworkService() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request.Builder requestBuilder = original
                    .newBuilder()
                    .header("Content-Type", "application/json")
                    .header("trakt-api-version", "2")
                    .header("trakt-api-key", "a92265d647322f33c64824cbd53366ad7fe29c8f80834b770d299405ca04801b");


            Request request = requestBuilder.build();
            return chain.proceed(request);

        });

        mRetrofit = builder.build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public APIService getAPIService() {
        return mRetrofit.create(APIService.class);
    }


}
