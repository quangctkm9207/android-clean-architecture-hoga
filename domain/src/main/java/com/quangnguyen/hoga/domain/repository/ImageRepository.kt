package com.quangnguyen.hoga.domain.repository

import com.quangnguyen.hoga.domain.model.Image
import io.reactivex.Single

interface ImageRepository {

  fun loadTrendingImage(): Single<List<Image>>

  fun searchImage(keyword: String): Single<List<Image>>
}