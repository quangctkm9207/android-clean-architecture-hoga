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

class GetImageUseCaseTest {

  private lateinit var imageRepository: ImageRepository
  private lateinit var getImageUseCase: GetImageUseCase

  private lateinit var testObserver: TestObserver<Image>

  @Before
  fun setup() {
    imageRepository = mock()
    getImageUseCase = GetImageUseCase(imageRepository)

    testObserver = TestObserver()
  }

  @Test
  fun shouldReturnImageWithCorrespondingIdIfItIsAvailable() {
    val imageId = "id1"
    val image = Image(imageId, "smallImageUrl", "downloadUrl", "authorName")
    given(imageRepository.getImage(imageId)).willReturn(Single.just(image))

    getImageUseCase.execute(imageId).subscribe(testObserver)

    then(imageRepository).should(times(1)).getImage(imageId)
    then(imageRepository).shouldHaveNoMoreInteractions()

    testObserver.assertValue(image)
    testObserver.assertComplete()
  }
}