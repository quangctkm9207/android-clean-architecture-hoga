package com.quangnguyen.hoga.domain.interactor.image

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.then
import com.nhaarman.mockito_kotlin.times
import com.quangnguyen.hoga.domain.model.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class LoadDownloadedImagesUseCaseTest {

  private lateinit var imageRepository: ImageRepository
  private lateinit var loadDownloadedImagesUseCase: LoadDownloadedImagesUseCase

  private lateinit var testObserver: TestObserver<List<Image>>

  @Before
  fun setup() {
    imageRepository = mock()
    loadDownloadedImagesUseCase = LoadDownloadedImagesUseCase(imageRepository)
    testObserver = TestObserver()
  }

  @Test
  fun shouldReturnDownloadedImage() {
    given(imageRepository.loadDownloadedImages()).willReturn(Single.just(TESTING_CARTRIDGES))

    loadDownloadedImagesUseCase.execute().subscribe(testObserver)

    then(imageRepository).should(times(1)).loadDownloadedImages()
    then(imageRepository).shouldHaveNoMoreInteractions()

    testObserver.assertComplete()
    testObserver.assertValue(TESTING_CARTRIDGES)
  }
}