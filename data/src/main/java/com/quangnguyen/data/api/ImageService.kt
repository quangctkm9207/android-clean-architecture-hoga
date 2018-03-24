package com.quangnguyen.data.api

import com.quangnguyen.data.model.ImageModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface ImageService {
  @GET("photos")
  fun loadTrendingImages(@Header(ApiConfig.AUTH_HEADER) token: String): Single<List<ImageModel>>

  @GET("search/photos")
  fun searchImages(@Header(ApiConfig.AUTH_HEADER) token: String, @Query(
      ApiConfig.QUERY) keyword: String): Single<List<ImageModel>>
}