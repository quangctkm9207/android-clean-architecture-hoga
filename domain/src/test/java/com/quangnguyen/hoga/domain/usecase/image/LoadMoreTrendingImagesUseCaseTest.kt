package com.quangnguyen.hoga.domain.usecase.image

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.then
import com.nhaarman.mockito_kotlin.times
import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.Before
import org.junit.Test

class LoadMoreTrendingImagesUseCaseTest {

  private lateinit var imageRepository: ImageRepository
  private lateinit var loadMoreTrendingImagesUseCase: LoadMoreTrendingImagesUseCase

  private lateinit var testObserver: TestObserver<List<Image>>

  @Before
  fun setup(){
    imageRepository = mock()
    loadMoreTrendingImagesUseCase = LoadMoreTrendingImagesUseCase(imageRepository)
    testObserver = TestObserver()
  }

  @Test
  fun shouldReturnImages() {
    given(imageRepository.loadMoreTrendingImages()).willReturn(Single.just(TESTING_CARTRIDGES))

    loadMoreTrendingImagesUseCase.execute().subscribe(testObserver)

    then(imageRepository).should(times(1)).loadMoreTrendingImages()
    then(imageRepository).shouldHaveNoMoreInteractions()

    testObserver.assertComplete()
    testObserver.assertValue(TESTING_CARTRIDGES)
  }
}