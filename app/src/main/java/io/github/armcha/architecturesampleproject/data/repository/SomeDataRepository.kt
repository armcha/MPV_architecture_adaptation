package io.github.armcha.architecturesampleproject.data.repository

import android.util.Log
import io.github.armcha.architecturesampleproject.di.scope.PerActivity
import io.github.armcha.architecturesampleproject.domain.model.User
import io.github.armcha.architecturesampleproject.domain.repository.SomeRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@PerActivity
class SomeDataRepository @Inject constructor() : SomeRepository {

    override fun getUser(): Flowable<User> {
        return Flowable.timer(10, TimeUnit.SECONDS)
                .map { User("A", "B") }
    }

    override fun saveUser(user: User): Completable {
        return Completable.timer(10, TimeUnit.SECONDS)
                .doOnComplete {
                    Log.d("USER", "USER SAVED ${user.name}")
                }
    }
}