package io.github.armcha.architecturesampleproject.domain.interactor

import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.model.User
import io.github.armcha.architecturesampleproject.domain.repository.SomeRepository
import io.reactivex.Flowable
import javax.inject.Inject


@PerScreen
class SomeInteractor @Inject constructor(private val someRepository: SomeRepository) {

    fun getUser(): Flowable<User> = someRepository.getUser() // Do some mappings

    fun saveUser(user: User) {
        someRepository.saveUser(user)
    }
}