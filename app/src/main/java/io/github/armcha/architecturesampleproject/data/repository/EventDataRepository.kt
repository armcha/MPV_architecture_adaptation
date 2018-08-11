package io.github.armcha.architecturesampleproject.data.repository

import io.github.armcha.architecturesampleproject.data.api.SomeApiService
import io.github.armcha.architecturesampleproject.data.local.SomeLocalCache
import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.model.Event
import io.github.armcha.architecturesampleproject.domain.repository.EventRepository
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@PerScreen
class EventDataRepository @Inject constructor(private val someApiService: SomeApiService,
                                              private val someLocalCache: SomeLocalCache) : EventRepository {

    override fun getDummyEvents(): Observable<List<Event>> {
        return if (Random().nextBoolean()) {
            val events = listOf(Event("1"),
                    Event("3"),
                    Event("5"),
                    Event("7"))
            Observable.timer(7, TimeUnit.SECONDS).map { events }
        } else {
            Observable.error(Throwable("404"))
        }

    }

}