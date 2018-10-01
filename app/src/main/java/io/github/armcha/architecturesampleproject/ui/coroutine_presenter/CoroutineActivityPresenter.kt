package io.github.armcha.architecturesampleproject.ui.coroutine_presenter

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.fetcher.Status
import io.github.armcha.architecturesampleproject.domain.fetcher.result_listener.RequestType
import io.github.armcha.architecturesampleproject.domain.interactor.PhoneInteractor
import io.github.armcha.architecturesampleproject.domain.model.Phone
import io.github.armcha.architecturesampleproject.ui.base.CoroutineBasePresenter
import io.github.armcha.architecturesampleproject.ui.util.NonNullObserver
import javax.inject.Inject

@PerScreen
class CoroutineActivityPresenter @Inject constructor(private val phoneInteractor: PhoneInteractor)
    : CoroutineBasePresenter<CoroutineActivityContract.View>(), CoroutineActivityContract.Presenter {

    private val liveData = MutableLiveData<List<Phone>>()

    @OnLifecycleEvent(value = Lifecycle.Event.ON_START)
    fun onStart() {
        Log.e("GET_PHONES", "STATUS IS ${RequestType.GET_PHONES.status.javaClass.simpleName}")
        Log.e("HEAVY_WORK_WITH_RESULT", "STATUS IS ${RequestType.HEAVY_WORK_WITH_RESULT.status.javaClass.simpleName}")
        Log.e("SAVE_DUMMY_DATA", "STATUS IS ${RequestType.SAVE_DUMMY_DATA.status.javaClass.simpleName}")

        when (RequestType.GET_PHONES.status) {
            is Status.Loading -> view?.showPhonesLoading()
        }

        when (RequestType.HEAVY_WORK_WITH_RESULT.status) {
            is Status.Loading -> view?.showPhonesLoading() //TODO
        }

        liveData.observe(this, NonNullObserver(view!!::showPhones))
    }

    override fun onPresenterCreate() {
        super.onPresenterCreate()

        fetch(phoneInteractor.getPhones(), RequestType.GET_PHONES, liveData::setValue)

        //phoneInteractor.getPhones().fetch(RequestType.GET_PHONES, liveData::setValue)

        complete(phoneInteractor::saveDummyData, RequestType.SAVE_DUMMY_DATA) {
            Log.e("saveDummyData ", "complete")
        }
    }

    override fun onRequestError(requestType: RequestType, throwable: Throwable) {
        super.onRequestError(requestType, throwable)
        Log.e("onRequestError ", "For type $requestType with error ${throwable.message}")
        view?.showPhonesLoadError()
    }

    override fun onRequestStart(requestType: RequestType) {
        super.onRequestStart(requestType)
        Log.e("onRequestStart ", "For type $requestType started")
    }
}