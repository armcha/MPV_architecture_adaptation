package io.github.armcha.architecturesampleproject.ui.base

import io.armcha.base.BaseMVPPresenter
import io.github.armcha.architecturesampleproject.domain.fetcher.CoroutineFetcher
import io.github.armcha.architecturesampleproject.domain.fetcher.Status
import io.github.armcha.architecturesampleproject.domain.fetcher.result_listener.RequestType
import io.github.armcha.architecturesampleproject.domain.fetcher.result_listener.ResultListener
import kotlinx.coroutines.Deferred
import javax.inject.Inject

abstract class CoroutineBasePresenter<V : BaseContract.View>
    : BaseMVPPresenter<V>(), BaseContract.Presenter<V>, ResultListener {

    @Inject
    protected lateinit var coroutineFetcher: CoroutineFetcher

    override fun onPresenterDestroy() {
        super.onPresenterDestroy()
        coroutineFetcher clear this
    }

    @JvmName("fetchWithKey")
    protected fun <T> Deferred<T>.fetch(requestType: RequestType = RequestType.TYPE_NONE, success: (T) -> Unit) {
        fetch(this, requestType, success)
    }

    protected val RequestType.status: Status
        get() {
            return coroutineFetcher.getRequestStatus(this@CoroutineBasePresenter, this)
        }

    protected infix fun RequestType.statusIs(status: Status) = this.status == status

    protected fun changeRequestStatus(requestType: RequestType, newStatus: Status) {
        coroutineFetcher.changeRequestStatus(this, requestType, newStatus)
    }

    protected fun <TYPE> fetch(deferred: Deferred<TYPE>,
                               requestType: RequestType = RequestType.TYPE_NONE, success: (TYPE) -> Unit) {
        coroutineFetcher.fetch(deferred, requestType, this, success)
    }

    protected fun <TYPE> fetch(body: suspend () -> TYPE, requestType: RequestType = RequestType.TYPE_NONE,
                               success: (TYPE) -> Unit) {
        coroutineFetcher.fetch(body, requestType, this, success)
    }

    protected fun complete(body: suspend () -> Unit,
                           requestType: RequestType = RequestType.TYPE_NONE, success: () -> Unit = {}) {
        coroutineFetcher.complete(body, requestType, this, success)
    }
}