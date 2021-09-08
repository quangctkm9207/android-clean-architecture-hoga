package com.quangnguyen.data.api

import com.quangnguyen.data.model.ImageModel
import com.quangnguyen.data.model.SearchResultModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface ImageService {

  @GET("photos")
  fun loadTrendingImages(@Header(ApiConfig.AUTH_HEADER) token: String,
      @Query(ApiConfig.PAGE) pageNum: Int,
      @Query(ApiConfig.PER_PAGE) pictureNumPerPage: Int,
      @Query(ApiConfig.ORDER_BY) orderBy: String): Single<List<ImageModel>>

  @GET("search/photos")
  fun searchImages(@Header(ApiConfig.AUTH_HEADER) token: String,
      @Query(ApiConfig.QUERY) keyword: String,
      @Query(ApiConfig.PAGE) pageNum: Int,
      @Query(ApiConfig.PER_PAGE) pictureNumPerPage: Int,
      @Query(ApiConfig.ORDER_BY) orderBy: String): Single<SearchResultModel>
}