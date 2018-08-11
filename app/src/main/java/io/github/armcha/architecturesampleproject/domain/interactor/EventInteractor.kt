package io.github.armcha.architecturesampleproject.domain.interactor

import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.model.Event
import io.github.armcha.architecturesampleproject.domain.model.User
import io.github.armcha.architecturesampleproject.domain.repository.EventRepository
import io.github.armcha.architecturesampleproject.domain.repository.SomeRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

@PerScreen
class EventInteractor @Inject constructor(private val eventRepository: EventRepository) {

    fun getEvents(): Observable<List<Event>> = eventRepository.getDummyEvents()

}