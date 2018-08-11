package io.github.armcha.architecturesampleproject.ui.presenter_with_livedata

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.fetcher.Status
import io.github.armcha.architecturesampleproject.domain.fetcher.result_listener.RequestType
import io.github.armcha.architecturesampleproject.domain.model.Event
import io.github.armcha.architecturesampleproject.domain.repository.EventRepository
import io.github.armcha.architecturesampleproject.ui.base.BasePresenter
import io.github.armcha.architecturesampleproject.ui.util.NonNullObserver
import javax.inject.Inject

@PerScreen
class ThirdActivityPresenter @Inject constructor(private val eventRepository: EventRepository)
    : BasePresenter<ThirdActivityContract.View>(), ThirdActivityContract.Presenter {

    private val liveData = MutableLiveData<List<Event>>()

    @OnLifecycleEvent(value = Lifecycle.Event.ON_START)
    fun onStart() {
        Log.e("GET_EVENTS", "STATUS IS ${GET_EVENTS.status.javaClass.simpleName}")
        when (GET_EVENTS.status) {
            is Status.Loading -> view?.showEventsLoading()
            is Status.Error -> view?.showEventsLoadError()
        }

        liveData.observe(this, NonNullObserver {
            view?.showEvents(it)
        })
    }

    override fun onPresenterCreate() {
        super.onPresenterCreate()
        eventRepository.getDummyEvents().fetch(GET_EVENTS, liveData::setValue)
    }

    override fun onRequestError(requestType: RequestType, throwable: Throwable) {
        super.onRequestError(requestType, throwable)
        view?.showEventsLoadError()
    }
}