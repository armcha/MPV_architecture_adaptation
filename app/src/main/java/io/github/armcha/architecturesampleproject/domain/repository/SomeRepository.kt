package io.github.armcha.architecturesampleproject.domain.repository

import io.github.armcha.architecturesampleproject.domain.model.User
import io.reactivex.Completable
import io.reactivex.Flowable


interface SomeRepository {

    fun getUser(): Flowable<User>

    fun saveUser(user: User): Completable
}