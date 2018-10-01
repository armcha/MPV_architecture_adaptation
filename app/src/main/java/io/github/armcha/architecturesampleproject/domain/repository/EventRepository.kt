package io.github.armcha.architecturesampleproject.domain.repository

import io.github.armcha.architecturesampleproject.domain.model.Event
import io.reactivex.Observable

interface EventRepository {

    fun getDummyEvents(): Observable<List<Event>>
}