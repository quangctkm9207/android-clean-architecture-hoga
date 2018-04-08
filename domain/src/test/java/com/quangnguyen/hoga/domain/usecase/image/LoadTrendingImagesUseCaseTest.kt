package com.quangnguyen.hoga.domain.usecase.image

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.then
import com.nhaarman.mockito_kotlin.times
import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class LoadTrendingImagesUseCaseTest {

  private lateinit var imageRepository: ImageRepository
  private lateinit var loadTrendingImagesUseCase: LoadTrendingImagesUseCase

  private lateinit var testObserver: TestObserver<List<Image>>

  @Before
  fun setup(){
    imageRepository = mock()
    loadTrendingImagesUseCase = LoadTrendingImagesUseCase(imageRepository)
    testObserver = TestObserver()
  }

  @Test
  fun shouldReturnImages() {
    given(imageRepository.loadTrendingImages()).willReturn(Single.just(TESTING_CARTRIDGES))

    loadTrendingImagesUseCase.execute().subscribe(testObserver)

    then(imageRepository).should(times(1)).loadTrendingImages()
    then(imageRepository).shouldHaveNoMoreInteractions()

    testObserver.assertComplete()
    testObserver.assertValue(TESTING_CARTRIDGES)
  }
}