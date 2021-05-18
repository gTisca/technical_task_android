/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.data.network.config

import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal const val BASE_URL = "https://gorest.co.in/public-api/"

private const val AUTHORIZATION_HEADER_KEY = "Authorization"
private const val AUTHORIZATION_HEADER_VALUE = "Bearer "
private const val ACCESS_TOKEN = "cf9c68cb362c695b93ba59019c210687eed6a2e6dd5e8eb0ff65a5a9a3cfe9c9"

internal fun createHttpClient(debug: Boolean): OkHttpClient {
    val clientBuilder = OkHttpClient.Builder().apply {
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(10, TimeUnit.SECONDS)

        addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_VALUE + ACCESS_TOKEN)
                .build()

            chain.proceed(request)
        }

        if (debug) {
            val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(httpLoggingInterceptor)
        }
    }
    return clientBuilder.build()
}

internal fun createNetworkClient(url: String, gson: Gson, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()
}