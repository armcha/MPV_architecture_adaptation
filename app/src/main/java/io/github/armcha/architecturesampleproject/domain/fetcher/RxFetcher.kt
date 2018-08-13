package io.github.armcha.architecturesampleproject.domain.fetcher

import io.github.armcha.architecturesampleproject.di.qualifier.IoScheduler
import io.github.armcha.architecturesampleproject.di.qualifier.UIScheduler
import io.github.armcha.architecturesampleproject.domain.fetcher.result_listener.RequestType
import io.github.armcha.architecturesampleproject.domain.fetcher.result_listener.ResultListener
import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RxFetcher @Inject constructor(@IoScheduler
                                  private val ioScheduler: Scheduler,
                                    @UIScheduler
                                  private val uiScheduler: Scheduler) {

    private val disposableMap = ConcurrentHashMap<String, CompositeDisposable>()
    private val requestMap = ConcurrentHashMap<String, ConcurrentHashMap<RequestType, Status>>()
    private val ResultListener.key
        get() = javaClass.name

    fun <T> fetch(flowable: Flowable<T>, requestType: RequestType,
                  resultListener: ResultListener, success: (T) -> Unit) {
        createOrGetDisposable(resultListener).add(flowable
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .doOnSubscribe { resultListener startAndAdd requestType }
                .subscribe(resultListener.onSuccess(requestType, success),
                        resultListener sendErrorFor requestType))
    }

    fun <T> fetch(observable: Observable<T>, requestType: RequestType,
                  resultListener: ResultListener, success: (T) -> Unit) {
        createOrGetDisposable(resultListener).add(observable
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .doOnSubscribe { resultListener startAndAdd requestType }
                .subscribe(resultListener.onSuccess(requestType, success),
                        resultListener sendErrorFor requestType))
    }

    fun <T> fetch(single: Single<T>, requestType: RequestType,
                  resultListener: ResultListener, success: (T) -> Unit) {
        createOrGetDisposable(resultListener).add(single
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .doOnSubscribe { resultListener startAndAdd requestType }
                .subscribe(resultListener.onSuccess(requestType, success),
                        resultListener sendErrorFor requestType))
    }

    fun complete(completable: Completable, requestType: RequestType,
                 resultListener: ResultListener, success: () -> Unit) {
        createOrGetDisposable(resultListener).add(completable
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .doOnSubscribe { resultListener startAndAdd requestType }
                .subscribe({
                    changeRequestStatus(resultListener, requestType, Status.Success)
                    success()
                }, resultListener sendErrorFor requestType))
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

    private fun createOrGetDisposable(resultListener: ResultListener): CompositeDisposable {
        return disposableMap.getOrPut(resultListener.key) {
            CompositeDisposable()
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