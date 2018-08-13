package io.github.armcha.architecturesampleproject.domain.fetcher

import io.github.armcha.architecturesampleproject.di.qualifier.BgContext
import io.github.armcha.architecturesampleproject.di.qualifier.UIContext
import io.github.armcha.architecturesampleproject.domain.fetcher.result_listener.RequestType
import io.github.armcha.architecturesampleproject.domain.fetcher.result_listener.ResultListener
import kotlinx.coroutines.experimental.*
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton


/**
 *
 * Created by Arman Chatikyan on 13 Aug 2018
 *
 */

@Singleton
class CoroutineFetcher @Inject constructor(@BgContext
                                           private val bgContext: CoroutineDispatcher,
                                           @UIContext
                                           private val uiContext: CoroutineDispatcher) {


    private val jobMap = ConcurrentHashMap<String, MutableList<Job>>()
    private val requestMap = ConcurrentHashMap<String, ConcurrentHashMap<RequestType, Status>>()

    private val ResultListener.key
        get() = javaClass.name

    fun <T> fetch(deferred: Deferred<T>, requestType: RequestType,
                  resultListener: ResultListener, success: (T) -> Unit) {
        createOrGetJob(resultListener).add(launch(uiContext) {
            resultListener startAndAdd requestType
            try {
                val result = withContext(bgContext) { deferred.await() }
                resultListener.onSuccess(requestType, result, success)
            } catch (exception: Exception) {
                resultListener sendErrorFor requestType
            }
        })
    }

    fun <T> fetch(body:suspend () -> T, requestType: RequestType,
                  resultListener: ResultListener, success: (T) -> Unit) {
        createOrGetJob(resultListener).add(launch(uiContext) {
            resultListener startAndAdd requestType
            try {
                val result = withContext(bgContext) { body() }
                resultListener.onSuccess(requestType, result, success)
            } catch (exception: Exception) {
                resultListener sendErrorFor requestType
            }
        })
    }

    fun complete(body: suspend () -> Unit, requestType: RequestType,
                 resultListener: ResultListener, success: () -> Unit) {
        createOrGetJob(resultListener).add(launch(uiContext) {
            resultListener startAndAdd requestType
            try {
                withContext(bgContext) { body() }
                changeRequestStatus(resultListener, requestType, Status.Success)
                success()
            } catch (exception: Exception) {
                resultListener sendErrorFor requestType
            }
        })
    }

    private fun <T> ResultListener.onSuccess(requestType: RequestType, result: T, success: (T) -> Unit) {
        val status = if (result is List<*> && result.isEmpty()) {
            Status.EmptySuccess
        } else {
            Status.Success
        }
        changeRequestStatus(this, requestType, status)
        success(result)
    }

    suspend fun <T> Deferred<T>.awaitSafe(): T? {
        var result: T? = null
        try {
            result = await()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    private fun createOrGetJob(resultListener: ResultListener): MutableList<Job> {
        return jobMap.getOrPut(resultListener.key) {
            mutableListOf()
        }
    }

    infix fun clear(resultListener: ResultListener) {
        val key = resultListener.key
        jobMap.run {
            if (containsKey(key)) {
                val list = this[key]
                list?.forEach {
                    it.cancel()
                }
                list?.clear()
                remove(key)
            }
        }
        requestMap.remove(key)
    }

//    fun complete(deferred: Deferred<T>, requestType: RequestType,
//                 resultListener: ResultListener, success: () -> Unit) {
//
//        createOrGetDisposable(resultListener).add(completable
//                .subscribeOn(ioScheduler)
//                .observeOn(uiScheduler)
//                .doOnSubscribe { resultListener startAndAdd requestType }
//                .subscribe({
//                    changeRequestStatus(resultListener, requestType, Status.Success)
//                    success()
//                }, resultListener sendErrorFor requestType))
//    }

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

    private fun <K, V> concurrentMapOf(vararg pair: Pair<K, V>): ConcurrentHashMap<K, V> {
        val map = ConcurrentHashMap<K, V>()
        pair.forEach {
            map[it.first] = it.second
        }
        return map
    }
}