package io.github.armcha.architecturesampleproject.domain.interactor

import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.model.User
import io.github.armcha.architecturesampleproject.domain.repository.SomeRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject


@PerScreen
class SomeInteractor @Inject constructor(private val someRepository: SomeRepository) {

    fun getUser(): Flowable<List<User>> = someRepository.getUser() // Do some mappings

    fun saveUser(user: User): Completable {
        return someRepository.saveUser(user)
    }
}