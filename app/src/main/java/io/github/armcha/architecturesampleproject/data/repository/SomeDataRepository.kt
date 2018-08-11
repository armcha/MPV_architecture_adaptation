package io.github.armcha.architecturesampleproject.data.repository

import android.util.Log
import io.github.armcha.architecturesampleproject.data.api.SomeApiService
import io.github.armcha.architecturesampleproject.data.local.SomeLocalCache
import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.model.User
import io.github.armcha.architecturesampleproject.domain.repository.SomeRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@PerScreen
class SomeDataRepository @Inject constructor(private val someApiService: SomeApiService,
                                             private val someLocalCache: SomeLocalCache) : SomeRepository {

    override fun getUser(): Flowable<List<User>> {
        return if (someLocalCache.hasCachedUsers()) {
            someLocalCache.getCachedUsers()
        } else {
            someApiService.getUsersList()
        }
    }

    override fun saveUser(user: User): Completable {
        return Completable.timer(5, TimeUnit.SECONDS)
                .doOnComplete {
                    Log.d("USER", "USER SAVED ${user.name}")
                }
    }
}