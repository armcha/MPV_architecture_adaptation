package io.github.armcha.architecturesampleproject.data.local

import io.github.armcha.architecturesampleproject.domain.model.User
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SomeLocalCache @Inject constructor() {

    fun hasCachedUsers() = true

    fun getCachedUsers(): Flowable<List<User>> {
        val users = listOf(User("1", "2"),
                User("3", "4"),
                User("5", "6"),
                User("7", "8"))
        return Flowable.timer(7,TimeUnit.SECONDS)
                .map { users }
    }
}