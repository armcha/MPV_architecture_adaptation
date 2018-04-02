package io.github.armcha.architecturesampleproject.domain.interactor

import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.model.User
import io.github.armcha.architecturesampleproject.domain.repository.SecondRepository
import io.github.armcha.architecturesampleproject.domain.repository.SomeRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject


@PerScreen
class SecondInteractor @Inject constructor(private val secondRepository: SecondRepository) {


}