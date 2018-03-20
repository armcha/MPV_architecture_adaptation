package io.github.armcha.architecturesampleproject.ui.base

import android.support.annotation.CallSuper
import android.util.Log
import io.armcha.arch.BaseMVPPresenter
import io.github.armcha.architecturesampleproject.domain.fetcher.Fetcher
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

    override fun onPresenterCreate() {
        super.onPresenterCreate()
        Log.e("BasePresenter", "onPresenterCreate  ${hashCode()}")
    }

    init {
        Log.e("BasePresenter", "init  ${hashCode()}")
    }

    fun <TYPE> fetch(flowable: Flowable<TYPE>,
                     requestType: RequestType = RequestType.TYPE_NONE, success: (TYPE) -> Unit) {
        fetcher.fetch(flowable, requestType, this, success)
    }

    fun <TYPE> fetch(observable: Observable<TYPE>,
                     requestType: RequestType = RequestType.TYPE_NONE, success: (TYPE) -> Unit) {
        fetcher.fetch(observable, requestType, this, success)
    }

    fun <TYPE> fetch(single: Single<TYPE>,
                     requestType: RequestType = RequestType.TYPE_NONE, success: (TYPE) -> Unit) {
        fetcher.fetch(single, requestType, this, success)
    }

    fun <TYPE> fetch(single: Single<TYPE>,
                     requestType: RequestType = RequestType.TYPE_NONE, success: (TYPE) -> Unit,
                     onError: (Throwable) -> Unit) {
        fetcher.fetch(single, requestType, this, success, onError)
    }

    fun complete(completable: Completable,
                 requestType: RequestType = RequestType.TYPE_NONE, success: () -> Unit = {}) {
        fetcher.complete(completable, requestType, this, success)
    }

    fun complete(body: () -> Any,
                 requestType: RequestType = RequestType.TYPE_NONE, success: () -> Unit = {}) {
        fetcher.complete(Completable.fromAction { body() }, requestType, this, success)
    }

    @CallSuper
    override fun onPresenterDestroy() {
        super.onPresenterDestroy()
        fetcher.clear(this)
    }
}