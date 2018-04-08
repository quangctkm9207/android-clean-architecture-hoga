package com.quangnguyen.hoga.domain.repository

import com.quangnguyen.hoga.domain.entity.Image
import io.reactivex.Completable
import io.reactivex.Single

interface ImageRepository {

  fun loadTrendingImages(): Single<List<Image>>

  fun searchImages(keyword: String): Single<List<Image>>

  fun getImage(imageId: String): Single<Image>

  fun loadDownloadedImages(): Single<List<Image>>

  fun downloadImage(image: Image): Completable

  fun setWallpaper(image: Image): Completable
}