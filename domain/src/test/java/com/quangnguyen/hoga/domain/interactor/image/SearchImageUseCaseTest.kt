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

class SearchImageUseCaseTest {

  private lateinit var imageRepository: ImageRepository
  private lateinit var searchImageUseCase: SearchImageUseCase

  private lateinit var testObserver: TestObserver<List<Image>>

  @Before
  fun setup() {
    imageRepository = mock()
    searchImageUseCase = SearchImageUseCase(imageRepository)
    testObserver = TestObserver()
  }

  @Test
  fun shouldReturnImagesIfTheyAreAvailable() {
    val keyword = "orange"


    given(imageRepository.searchImage(keyword)).willReturn(Single.just(TESTING_CARTRIDGES))

    searchImageUseCase.execute(keyword).subscribe(testObserver)

    then(imageRepository).should(times(1)).searchImage(keyword)
    then(imageRepository).shouldHaveNoMoreInteractions()

    testObserver.assertComplete()
    testObserver.assertValue(TESTING_CARTRIDGES)
  }
}