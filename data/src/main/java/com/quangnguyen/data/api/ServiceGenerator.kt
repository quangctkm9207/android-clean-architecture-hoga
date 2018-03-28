package com.quangnguyen.data.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Creates api related implementations.
 */
object ServiceGenerator {

  fun provideImageService(): ImageService {
    return provideRetrofit(ApiConfig.IMAGE_BASE_HOST).create(ImageService::class.java)
  }

  private fun provideRetrofit(baseUrl: String): Retrofit {
    return Retrofit.Builder().baseUrl(baseUrl)
        .client(provideOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(provideGson()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
  }

  private fun provideGson(): Gson {
    return GsonBuilder()
        .create()
  }

  private fun provideOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BASIC)

    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()
  }
}