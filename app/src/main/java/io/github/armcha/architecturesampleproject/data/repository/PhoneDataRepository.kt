package io.github.armcha.architecturesampleproject.data.repository

import android.os.SystemClock
import io.github.armcha.architecturesampleproject.data.api.SomeApiService
import io.github.armcha.architecturesampleproject.data.local.SomeLocalCache
import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.model.Phone
import io.github.armcha.architecturesampleproject.domain.repository.PhoneRepository
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import java.util.*
import javax.inject.Inject

@PerScreen
class PhoneDataRepository @Inject constructor(private val someApiService: SomeApiService,
                                              private val someLocalCache: SomeLocalCache) : PhoneRepository {

    override fun getDummyPhones(): Deferred<List<Phone>> {
        return async {
            SystemClock.sleep(5000)
            if (Random().nextBoolean()) {
                throw IllegalStateException("Ooops you are unlucky")
            }
            listOf(Phone("A"), Phone("B"), Phone("C"), Phone("D"))
        }
    }

    override suspend fun saveDummyData() {
        SystemClock.sleep(10000)
        if (Random().nextBoolean()) {
            throw IllegalStateException("Ooops you are unlucky")
        }
    }

    override suspend fun doSomeHeavyWork(): String {
        SystemClock.sleep(2000)
        if (Random().nextBoolean()) {
            throw IllegalStateException("Ooops you are unlucky")
        }
        return "Success"
    }
}