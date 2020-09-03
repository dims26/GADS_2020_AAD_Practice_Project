package com.dims.gads2020aadpracticeproject

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    @JvmStatic private val URL = "https://gadsapi.herokuapp.com/"

    @JvmStatic private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    @JvmStatic private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logger)

    @JvmStatic private val builder = Retrofit.Builder().baseUrl(URL)
        .client(okHttpClient.build())
        .addConverterFactory(GsonConverterFactory.create())

    @JvmStatic private val retrofit = builder.build()

    @JvmStatic
    fun <S> buildService(serviceType: Class<S>): S {
        return retrofit.create(serviceType)
    }
}