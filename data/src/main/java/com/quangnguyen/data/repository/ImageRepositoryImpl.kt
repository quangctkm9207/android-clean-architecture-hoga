package com.quangnguyen.data.repository

import com.quangnguyen.data.BuildConfig
import com.quangnguyen.data.api.ApiConfig
import com.quangnguyen.data.api.ImageService
import com.quangnguyen.data.mapper.ImageMapper
import com.quangnguyen.hoga.domain.model.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import io.reactivex.Flowable
import io.reactivex.Single

class ImageRepositoryImpl(private val imageService: ImageService,
    private val imageMapper: ImageMapper) : ImageRepository {
  private val token = BuildConfig.UNSPLASH_TOKEN

  private val caches = HashMap<String, Image>()

  override fun loadTrendingImages(): Single<List<Image>> {
    return imageService.loadTrendingImages(token, ApiConfig.DEFAULT_PAGE,
        ApiConfig.DEFAULT_PER_PAGE, ApiConfig.DEFAULT_ORDER_BY)
        .toFlowable()
        .flatMap { Flowable.fromIterable(it) }
        .map { imageMapper.dataToDomain(it) }
        .doOnNext{ caches[it.id] = it }
        .toList()
  }

  override fun searchImages(keyword: String): Single<List<Image>> {
    return imageService.searchImages(token, keyword, ApiConfig.DEFAULT_PAGE,
        ApiConfig.DEFAULT_PER_PAGE, ApiConfig.DEFAULT_ORDER_BY)
        .map { it.results }
        .toFlowable()
        .flatMap { Flowable.fromIterable(it) }
        .map { imageMapper.dataToDomain(it) }
        .doOnNext{ caches[it.id] = it }
        .toList()
  }

  override fun getImage(imageId: String): Single<Image> {
    return if (caches.containsKey(imageId)) {
      Single.just(caches[imageId])
    } else {
      Single.error(NoSuchElementException())
    }
  }
}