package com.quangnguyen.hoga.domain.usecase.image

import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.then
import org.mockito.kotlin.times
import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.TestObserver
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