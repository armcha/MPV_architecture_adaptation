package io.github.armcha.architecturesampleproject.domain.fetcher

import io.github.armcha.architecturesampleproject.domain.fetcher.result_listener.RequestType
import io.github.armcha.architecturesampleproject.domain.fetcher.result_listener.ResultListener
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.ConcurrentHashMap

abstract class AbstractFetcher {

    private val disposableMap = ConcurrentHashMap<String, CompositeDisposable>()
    private val requestMap = ConcurrentHashMap<String, ConcurrentHashMap<RequestType, Status>>()
    private val ResultListener.key
        get() = javaClass.name

    abstract fun <T> fetch(flowable: Flowable<T>, requestType: RequestType,
                           resultListener: ResultListener, success: (T) -> Unit)

    fun doOnStart(resultListener: ResultListener, requestType: RequestType) {
        resultListener startAndAdd requestType
    }

    fun <T> doOnSuccess(resultListener: ResultListener, requestType: RequestType, success: (T) -> Unit) {
        resultListener.onSuccess(requestType, success)
    }

    fun doOnError(resultListener: ResultListener, requestType: RequestType) {
        resultListener sendErrorFor requestType
    }

    private fun createOrGetDisposable(resultListener: ResultListener): CompositeDisposable {
        return disposableMap.getOrPut(resultListener.key) {
            CompositeDisposable()
        }
    }

    fun changeRequestStatus(resultListener: ResultListener, requestType: RequestType, status: Status) {
        val key = resultListener.key
        if (requestMap.containsKey(key)) {
            val currentRequest = requestMap[key]!! //FIXME
            currentRequest[requestType] = status
            requestMap.replace(key, currentRequest)
        }
    }

    fun getRequestStatus(resultListener: ResultListener, requestType: RequestType): Status {
        val currentRequest = requestMap[resultListener.key]
        return currentRequest?.get(requestType) ?: Status.Idle
    }

    infix fun clear(resultListener: ResultListener) {
        val key = resultListener.key
        disposableMap.run {
            if (containsKey(key)) {
                this[key]?.clear()
                remove(key)
            }
        }
        requestMap.remove(key)
    }

    private infix fun ResultListener.startAndAdd(requestType: RequestType) {
        onRequestStart(requestType)
        if (requestType != RequestType.TYPE_NONE) {
            if (requestMap.containsKey(key)) {
                changeRequestStatus(this, requestType, Status.Loading)
            } else {
                val status = Status.Loading as Status
                requestMap[key] = concurrentMapOf(requestType to status)
            }
        }
    }

    private infix fun ResultListener.sendErrorFor(requestType: RequestType): (Throwable) -> Unit {
        return {
            changeRequestStatus(this, requestType, Status.Error)
            onRequestError(requestType, it)
        }
    }

    private fun <T> ResultListener.onSuccess(requestType: RequestType, success: (T) -> Unit): (T) -> Unit {
        return {
            val status = if (it is List<*> && it.isEmpty()) {
                Status.EmptySuccess
            } else {
                Status.Success
            }
            changeRequestStatus(this, requestType, status)
            success(it)
        }
    }

    private fun <K, V> concurrentMapOf(vararg pair: Pair<K, V>): ConcurrentHashMap<K, V> {
        val map = ConcurrentHashMap<K, V>()
        pair.forEach {
            map[it.first] = it.second
        }
        return map
    }
}