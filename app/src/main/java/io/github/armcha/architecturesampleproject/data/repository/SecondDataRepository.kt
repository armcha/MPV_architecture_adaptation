package io.github.armcha.architecturesampleproject.data.repository

import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.repository.SecondRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

@PerScreen
class SecondDataRepository @Inject constructor() : SecondRepository {

    override fun doSomeDummyWork(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSomeDummyData(): Observable<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}