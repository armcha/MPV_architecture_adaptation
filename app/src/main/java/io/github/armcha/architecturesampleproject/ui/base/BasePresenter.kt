package io.github.armcha.architecturesampleproject.ui.base

import android.support.annotation.CallSuper
import io.armcha.arch.BaseMVPPresenter
import io.github.armcha.architecturesampleproject.domain.fetcher.Fetcher
import io.github.armcha.architecturesampleproject.domain.fetcher.Status
import io.github.armcha.architecturesampleproject.domain.fetcher.result_listener.RequestType
import io.github.armcha.architecturesampleproject.domain.fetcher.result_listener.ResultListener
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

abstract class BasePresenter<V : BaseContract.View>
    : BaseMVPPresenter<V>(), BaseContract.Presenter<V>, ResultListener {

    @Inject
    protected lateinit var fetcher: Fetcher

    protected val GET_USER = RequestType.GET_USER
    protected val SAVE_USER = RequestType.SAVE_USER

    protected val LOADING = Status.Loading
    protected val EMPTY_SUCCESS = Status.EmptySuccess
    protected val ERROR = Status.Error
    protected val SUCCESS = Status.Success

    @CallSuper
    override fun onPresenterDestroy() {
        super.onPresenterDestroy()
        fetcher clear this
    }

    @JvmName("fetchWithKey")
    protected fun <T> Flowable<T>.fetch(requestType: RequestType = RequestType.TYPE_NONE, success: (T) -> Unit) {
        fetch(this, requestType, success)
    }

    protected fun RequestType.currentStatus() = fetcher.getRequestStatus(this@BasePresenter, this)

    protected infix fun RequestType.statusIs(status: Status)
            = fetcher.getRequestStatus(this@BasePresenter, this) == status

    protected fun changeRequestStatus(requestType: RequestType, newStatus: Status) {
        fetcher.changeRequestStatus(this, requestType, newStatus)
    }

    protected fun <TYPE> fetch(flowable: Flowable<TYPE>,
                               requestType: RequestType = RequestType.TYPE_NONE, success: (TYPE) -> Unit) {
        fetcher.fetch(flowable, requestType, this, success)
    }

    protected fun <TYPE> fetch(observable: Observable<TYPE>,
                               requestType: RequestType = RequestType.TYPE_NONE, success: (TYPE) -> Unit) {
        fetcher.fetch(observable, requestType, this, success)
    }

    protected fun <TYPE> fetch(single: Single<TYPE>,
                               requestType: RequestType = RequestType.TYPE_NONE, success: (TYPE) -> Unit) {
        fetcher.fetch(single, requestType, this, success)
    }

    protected fun complete(completable: Completable,
                           requestType: RequestType = RequestType.TYPE_NONE, success: () -> Unit = {}) {
        fetcher.complete(completable, requestType, this, success)
    }

    protected fun complete(body: () -> Unit,
                           requestType: RequestType = RequestType.TYPE_NONE, success: () -> Unit = {}) {
        fetcher.complete(Completable.fromAction { body() }, requestType, this, success)
    }
}