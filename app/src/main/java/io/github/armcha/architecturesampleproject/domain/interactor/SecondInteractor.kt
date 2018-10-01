package io.github.armcha.architecturesampleproject.domain.interactor

import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.repository.SecondRepository
import javax.inject.Inject


@PerScreen
class SecondInteractor @Inject constructor(private val secondRepository: SecondRepository) {


}