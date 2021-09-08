package com.quangnguyen.data.repository

import androidx.core.os.OperationCanceledException
import com.quangnguyen.data.BuildConfig
import com.quangnguyen.data.api.ApiConfig
import com.quangnguyen.data.api.ImageService
import com.quangnguyen.data.database.ImageDao
import com.quangnguyen.data.device.ImageDownloader
import com.quangnguyen.data.device.WallpaperHelper
import com.quangnguyen.data.mapper.ImageMapper
import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

class ImageRepositoryImpl(private val imageService: ImageService,
    private val imageDao: ImageDao,
    private val imageDownloader: ImageDownloader,
    private val wallpaperHelper: WallpaperHelper,
    private val imageMapper: ImageMapper) : ImageRepository {
  private val token = BuildConfig.UNSPLASH_TOKEN

  // Monitor the page number of data loaded from remote source
  private var trendingPageNumber: Int = 1
  private var searchPageNumber: Int = 1

  override fun loadTrendingImages(): Single<List<Image>> {
    // Always load the first page
    trendingPageNumber = 1

    return loadTrendingImages(trendingPageNumber, ApiConfig.DEFAULT_PER_PAGE,
        ApiConfig.DEFAULT_ORDER_BY)
  }

  override fun loadMoreTrendingImages(): Single<List<Image>> {
    // Load the next page
    trendingPageNumber++

    return loadTrendingImages(trendingPageNumber, ApiConfig.DEFAULT_PER_PAGE,
        ApiConfig.DEFAULT_ORDER_BY)
  }

  private fun loadTrendingImages(pageNumber: Int, numberPerPage: Int,
      orderBy: String): Single<List<Image>> {
    return imageService.loadTrendingImages(token, pageNumber, numberPerPage, orderBy)
        .toFlowable()
        .flatMap { Flowable.fromIterable(it) }
        .doOnNext {
          imageDao.insertImage(it)
        }
        .map {
          imageMapper.dataToDomain(it)
        }
        .toList()
  }

  override fun searchImages(keyword: String): Single<List<Image>> {
    // Always load the first page
    searchPageNumber = 1

    return searchImages(keyword, searchPageNumber, ApiConfig.DEFAULT_PER_PAGE,
        ApiConfig.DEFAULT_ORDER_BY)
  }

  override fun searchMoreImages(keyword: String): Single<List<Image>> {
    // Load the next page
    searchPageNumber++

    return searchImages(keyword, searchPageNumber, ApiConfig.DEFAULT_PER_PAGE,
        ApiConfig.DEFAULT_ORDER_BY)
  }

  private fun searchImages(keyword: String, pageNumber: Int, numberPerPage: Int,
      orderBy: String): Single<List<Image>> {
    return imageService.searchImages(token, keyword, pageNumber,
        numberPerPage, orderBy)
        .map { it.results }
        .toFlowable()
        .flatMap { Flowable.fromIterable(it) }
        .doOnNext {
          imageDao.insertImage(it)
        }
        .map { imageMapper.dataToDomain(it) }
        .toList()
  }

  override fun getImage(imageId: String): Single<Image> {
    return imageDao.getImage(imageId)
        .map { imageMapper.dataToDomain(it) }
  }

  override fun downloadImage(image: Image): Completable {
    return if (image.downloadedFilePath != null) {
      Completable.error(
          OperationCanceledException("Image has been downloaded already.")
      )
    } else {
      val imageFileName = "${image.id}_${image.authorName.replace(" ", "")}"
      imageDownloader.download(image.downloadUrl, imageFileName)
          .toSingle { imageDao.getImage(image.id) }
          .flatMapCompletable {
          val imageModel = it.blockingGet()
          imageModel.downloadedFilePath = imageDownloader.getImageFilePath(imageFileName)
          imageDao.updateImage(imageModel)
          Completable.complete()
        }
    }
  }

  override fun loadDownloadedImages(): Single<List<Image>> {
    return imageDao.getImages()
        .toFlowable()
        .flatMap { Flowable.fromIterable(it) }
        .map { imageMapper.dataToDomain(it) }
        .filter { it.downloadedFilePath != null }
        .toList()
  }

  override fun setWallpaper(image: Image): Completable {
    return wallpaperHelper.setWallpaper(image.downloadUrl)
  }
}