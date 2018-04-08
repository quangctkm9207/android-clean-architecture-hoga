package com.quangnguyen.hoga.domain.usecase

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * A use case returns Flowable as a result.
 */
interface FlowableUseCase<R> {
  fun execute(): Flowable<R>
}

/**
 * A use case returns Flowable as a result and needs a parameter.
 */
interface FlowableUseCaseWithParam<P, R> {
  fun execute(param: P): Flowable<R>
}

/**
 * A use case returns Single as a result.
 */
interface SingleUseCase<R> {
  fun execute(): Single<R>
}

/**
 * A use case returns Single as a result and needs a parameter.
 */
interface SingleUseCaseWithParam<P, R> {
  fun execute(param: P): Single<R>
}

/**
 * A use case returns Completable as a result.
 */
interface CompletableUseCase {
  fun execute(): Completable
}

/**
 * A use case returns Completable as a result and needs a parameter.
 */
interface CompletableUseCaseWithParam<P> {
  fun execute(param: P): Completable
}