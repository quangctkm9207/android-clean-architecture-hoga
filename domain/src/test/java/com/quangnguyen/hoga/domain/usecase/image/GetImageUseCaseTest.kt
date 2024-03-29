package com.quangnguyen.hoga.domain.usecase.image

import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.then
import org.mockito.kotlin.times

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
    val image = Image(imageId, "smallImageUrl", "downloadUrl", "authorName", null)
    given(imageRepository.getImage(imageId)).willReturn(Single.just(image))

    getImageUseCase.execute(imageId).subscribe(testObserver)

    then(imageRepository).should(times(1)).getImage(imageId)
    then(imageRepository).shouldHaveNoMoreInteractions()

    testObserver.assertValue(image)
    testObserver.assertComplete()
  }
}