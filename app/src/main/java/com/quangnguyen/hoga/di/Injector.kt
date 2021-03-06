package com.quangnguyen.hoga.di

import android.app.Application
import com.quangnguyen.data.api.ServiceGenerator
import com.quangnguyen.data.database.DatabaseGenerator
import com.quangnguyen.data.device.ImageDownloader
import com.quangnguyen.data.device.WallpaperHelper
import com.quangnguyen.data.mapper.ImageMapperImpl
import com.quangnguyen.data.repository.ImageRepositoryImpl
import com.quangnguyen.hoga.domain.usecase.image.DownloadImageUseCase
import com.quangnguyen.hoga.domain.usecase.image.GetImageUseCase
import com.quangnguyen.hoga.domain.usecase.image.LoadDownloadedImagesUseCase
import com.quangnguyen.hoga.domain.usecase.image.LoadMoreTrendingImagesUseCase
import com.quangnguyen.hoga.domain.usecase.image.LoadTrendingImagesUseCase
import com.quangnguyen.hoga.domain.usecase.image.SearchImagesUseCase
import com.quangnguyen.hoga.domain.usecase.image.SearchMoreImagesUseCase
import com.quangnguyen.hoga.domain.usecase.image.SetWallpaperUseCase
import com.quangnguyen.hoga.util.SchedulerProvider

class Injector {

  companion object {
    lateinit var loadTrendingImagesUseCase: LoadTrendingImagesUseCase
    lateinit var loadMoreTrendingImagesUseCase: LoadMoreTrendingImagesUseCase
    lateinit var searchImagesUseCase: SearchImagesUseCase
    lateinit var searchMoreImagesUseCase: SearchMoreImagesUseCase
    lateinit var getImageUseCase: GetImageUseCase
    lateinit var downloadImageUseCase: DownloadImageUseCase
    lateinit var loadDownloadedImagesUseCase: LoadDownloadedImagesUseCase
    lateinit var setWallpaperUseCase: SetWallpaperUseCase

    lateinit var schedulerProvider: SchedulerProvider

    fun initialize(app: Application) {
      val imageService = ServiceGenerator.provideImageService()
      val imageDao = DatabaseGenerator.getImageDao(app)
      val imageMapper = ImageMapperImpl()
      val imageDownloader = ImageDownloader()
      val wallpaperHelper = WallpaperHelper(app)
      val imageRepository = ImageRepositoryImpl(imageService, imageDao, imageDownloader,
          wallpaperHelper,
          imageMapper)
      loadTrendingImagesUseCase = LoadTrendingImagesUseCase(imageRepository)
      loadMoreTrendingImagesUseCase = LoadMoreTrendingImagesUseCase(imageRepository)
      searchImagesUseCase = SearchImagesUseCase(imageRepository)
      searchMoreImagesUseCase = SearchMoreImagesUseCase(imageRepository)
      getImageUseCase = GetImageUseCase(imageRepository)
      downloadImageUseCase = DownloadImageUseCase(imageRepository)
      loadDownloadedImagesUseCase = LoadDownloadedImagesUseCase(imageRepository)
      setWallpaperUseCase = SetWallpaperUseCase(imageRepository)

      schedulerProvider = SchedulerProvider()
    }
  }
}