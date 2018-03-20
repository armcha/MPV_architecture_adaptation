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
class Fetcher @Inject constructor(@IoScheduler
                                  private val ioScheduler: Scheduler,
                                  @UIScheduler
                                  private val uiScheduler: Scheduler) {

    private val disposableMap = ConcurrentHashMap<String, CompositeDisposable>()
    private val requestMap = ConcurrentHashMap<RequestType, Status>()

    fun <T> fetch(flowable: Flowable<T>, requestType: RequestType,
                  resultListener: ResultListener, success: (T) -> Unit) {
        createOrGetDisposable(resultListener)?.add(flowable
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .doOnSubscribe { resultListener startAndAdd requestType }
                .subscribe(onSuccess<T>(requestType, success),
                        resultListener.onError(requestType)))
    }

    fun <T> fetch(observable: Observable<T>, requestType: RequestType,
                  resultListener: ResultListener, success: (T) -> Unit) {
        createOrGetDisposable(resultListener)?.add(observable
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .doOnSubscribe { resultListener startAndAdd requestType }
                .subscribe(onSuccess<T>(requestType, success),
                        resultListener.onError(requestType)))
    }

    fun <T> fetch(single: Single<T>, requestType: RequestType,
                  resultListener: ResultListener, success: (T) -> Unit) {
        createOrGetDisposable(resultListener)?.add(single
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .doOnSubscribe { resultListener startAndAdd requestType }
                .subscribe(onSuccess<T>(requestType, success),
                        resultListener.onError(requestType)))
    }

    fun <T> fetch(maybe: Maybe<T>, requestType: RequestType,
                  resultListener: ResultListener, success: (T) -> Unit) {
        createOrGetDisposable(resultListener)?.add(maybe
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .doOnSubscribe { resultListener startAndAdd requestType }
                .subscribe(onSuccess<T>(requestType, success),
                        resultListener.onError(requestType)))
    }

    fun <T> fetch(single: Single<T>, requestType: RequestType,
                  resultListener: ResultListener, success: (T) -> Unit, onError: (Throwable) -> Unit) {
        createOrGetDisposable(resultListener)?.add(single
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .doOnSubscribe { resultListener startAndAdd requestType }
                .subscribe({ onSuccess(requestType, success) }, { onError(it) }))
    }

    fun complete(completable: Completable, requestType: RequestType,
                 resultListener: ResultListener, success: () -> Unit) {
        createOrGetDisposable(resultListener)?.add(completable
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .doOnSubscribe { resultListener startAndAdd requestType }
                .subscribe({
                    requestMap.replace(requestType, Status.Success)
                    success()
                }, resultListener.onError(requestType)))
    }

    fun hasActiveRequest(): Boolean = requestMap.isNotEmpty()

    fun getRequestStatus(requestType: RequestType) = requestMap.getOrElse(requestType, { Status.Idle })

    fun removeRequest(requestType: RequestType) {
        requestMap.remove(requestType)
    }

    fun clear(resultListener: ResultListener) {
        val key: String = key(resultListener)
        with(disposableMap) {
            if (containsKey(key)) {
                this[key]?.clear()
                remove(key)
            }
        }
    }

    private infix fun ResultListener.startAndAdd(requestType: RequestType) {
        onRequestStart(requestType)
        if (requestType != RequestType.TYPE_NONE)
            requestMap[requestType] = Status.Loading
    }

    private fun ResultListener.onError(requestType: RequestType): (Throwable) -> Unit {
        return {
            requestMap.replace(requestType, Status.Error)
            onRequestError(requestType, it)
        }
    }

    private fun <T> onSuccess(requestType: RequestType, success: (T) -> Unit): (T) -> Unit {
        return {
            val status = if (it is List<*> && it.isEmpty()) {
                Status.EmptySuccess
            } else {
                Status.Success
            }
            requestMap.replace(requestType, status)
            success(it)
        }
    }

    private fun key(resultListener: ResultListener) = resultListener::class.java.name

    private fun createOrGetDisposable(resultListener: ResultListener): CompositeDisposable? {
        return disposableMap.getOrPut(key(resultListener)) {
            CompositeDisposable()
        }
    }
}