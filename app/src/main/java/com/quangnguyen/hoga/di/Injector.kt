package com.quangnguyen.hoga.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.quangnguyen.data.api.ApiConfig
import com.quangnguyen.data.api.ImageService
import com.quangnguyen.data.mapper.ImageMapperImpl
import com.quangnguyen.data.repository.ImageRepositoryImpl
import com.quangnguyen.hoga.domain.interactor.image.LoadTrendingImagesUseCase
import com.quangnguyen.hoga.domain.interactor.image.SearchImagesUseCase
import com.quangnguyen.hoga.util.SchedulerProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class Injector {

  companion object {
    lateinit var loadTrendingImagesUseCase: LoadTrendingImagesUseCase
    lateinit var searchImagesUseCase: SearchImagesUseCase

    lateinit var schedulerProvider: SchedulerProvider
    fun initialize() {
      val imageService = provideImageService()
      val imageMapper = ImageMapperImpl()
      val imageRepository = ImageRepositoryImpl(imageService, imageMapper)
      loadTrendingImagesUseCase = LoadTrendingImagesUseCase(imageRepository)
      searchImagesUseCase = SearchImagesUseCase(imageRepository)

      schedulerProvider = SchedulerProvider()
    }

    private fun provideImageService(): ImageService {
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
}