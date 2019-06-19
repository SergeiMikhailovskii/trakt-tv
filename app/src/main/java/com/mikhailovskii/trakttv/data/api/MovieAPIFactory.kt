package com.mikhailovskii.trakttv.data.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MovieAPIFactory private constructor() {

    private val retrofit: Retrofit

    val apiService: MovieAPI
        get() = retrofit.create(MovieAPI::class.java)

    init {
        val httpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                    .header(CONTENT_TYPE_HEADER, CONTENT_TYPE_VALUE)
                    .header(API_VERSION_HEADER, API_VERSION_VALUE)
                    .header(API_KEY_HEADER, API_KEY_VALUE)
                    .method(original.method(), original.body())
                    .build()

            chain.proceed(requestBuilder)
        }

        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    companion object {

        private const val CONTENT_TYPE_HEADER = "Content-Type"
        private const val API_VERSION_HEADER = "trakt-api-version"
        private const val API_KEY_HEADER = "trakt-api-key"

        private const val CONTENT_TYPE_VALUE = "application/json"
        private const val API_VERSION_VALUE = "2"
        private const val API_KEY_VALUE = "a92265d647322f33c64824cbd53366ad7fe29c8f80834b770d299405ca04801b"

        private const val BASE_URL = "https://api.trakt.tv"

        private var mInstance: MovieAPIFactory? = null

        fun getInstance(): MovieAPIFactory {
            if (mInstance == null) {
                mInstance = MovieAPIFactory()
            }
            return mInstance!!
        }
    }

}
