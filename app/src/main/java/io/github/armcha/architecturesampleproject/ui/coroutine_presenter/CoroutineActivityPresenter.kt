package io.github.armcha.architecturesampleproject.ui.coroutine_presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.fetcher.Status
import io.github.armcha.architecturesampleproject.domain.fetcher.result_listener.RequestType
import io.github.armcha.architecturesampleproject.domain.interactor.PhoneInteractor
import io.github.armcha.architecturesampleproject.domain.model.Phone
import io.github.armcha.architecturesampleproject.domain.repository.PhoneRepository
import io.github.armcha.architecturesampleproject.ui.base.CoroutineBasePresenter
import io.github.armcha.architecturesampleproject.ui.util.NonNullObserver
import javax.inject.Inject

@PerScreen
class CoroutineActivityPresenter @Inject constructor(private val phoneInteractor: PhoneInteractor)
    : CoroutineBasePresenter<CoroutineActivityContract.View>(), CoroutineActivityContract.Presenter {

    private val liveData = MutableLiveData<List<Phone>>()

    @OnLifecycleEvent(value = Lifecycle.Event.ON_START)
    fun onStart() {
        Log.e("GET_EVENTS", "STATUS IS ${RequestType.GET_PHONES.status.javaClass.simpleName}")
        when (RequestType.GET_PHONES.status) {
            is Status.Loading -> view?.showPhonesLoading()
            is Status.Error -> view?.showPhonesLoadError()
        }

        liveData.observe(this, NonNullObserver {
            view?.showPhones(it)
        })
    }

    override fun onPresenterCreate() {
        super.onPresenterCreate()
        fetch(phoneInteractor.getPhones(), RequestType.GET_PHONES) {
            liveData.value = it
        }
        complete({ phoneInteractor.saveDummyData() }, RequestType.TYPE_NONE) {
            Log.e("saveDummyData ", "complete")
        }
    }

    override fun onRequestError(requestType: RequestType, throwable: Throwable) {
        super.onRequestError(requestType, throwable)
        view?.showPhonesLoadError()
    }
}