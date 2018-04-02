package io.github.armcha.architecturesampleproject.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable

interface SecondRepository {

    fun doSomeDummyWork(): Completable

    fun getSomeDummyData(): Observable<String>
}