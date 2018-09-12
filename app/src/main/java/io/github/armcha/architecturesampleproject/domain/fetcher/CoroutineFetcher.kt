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
        createOrGetJobList(resultListener) += GlobalScope.launch(uiContext) {
            resultListener add requestType
            withContext(bgContext) { deferred.join() }
            if (!deferred.isCompletedExceptionally) {
                val result = deferred.getCompleted()
                resultListener.onSuccess(requestType, result, success)
            } else {
                val throwable = deferred.getCompletionExceptionOrNull()
                        ?: deferred.getCancellationException()
                resultListener.sendErrorFor(requestType, throwable)
            }
        }
    }

    fun <T> fetch(body: suspend () -> T, requestType: RequestType,
                  resultListener: ResultListener, success: (T) -> Unit) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            resultListener.sendErrorFor(requestType, throwable)
        }
        createOrGetJobList(resultListener) += GlobalScope.launch(uiContext + exceptionHandler) {
            resultListener add requestType
            val result = withContext(bgContext) { body() }
            resultListener.onSuccess(requestType, result, success)
        }
    }

    fun complete(body: suspend () -> Unit, requestType: RequestType,
                 resultListener: ResultListener, success: () -> Unit) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            resultListener.sendErrorFor(requestType, throwable)
        }
        createOrGetJobList(resultListener) += launch(uiContext + exceptionHandler) {
            resultListener add requestType
            withContext(bgContext) { body() }
            changeRequestStatus(resultListener, requestType, Status.Success)
            success()
        }
    }

    // TODO
    fun completeDeferred(deferred: CompletableDeferred<Unit>, requestType: RequestType,
                         resultListener: ResultListener, success: () -> Unit) {

        createOrGetJobList(resultListener).add(GlobalScope.launch(uiContext) {
            resultListener add requestType
            try {
                withContext(bgContext) {
                    deferred.await()
                    deferred.complete(Unit)
                }
                changeRequestStatus(resultListener, requestType, Status.Success)
                success()
            } catch (exception: Exception) {
                resultListener.sendErrorFor(requestType, exception)
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

    private fun createOrGetJobList(resultListener: ResultListener): MutableList<Job> {
        return jobMap.getOrPut(resultListener.key) { mutableListOf() }
    }

    infix fun clear(resultListener: ResultListener) {
        val key = resultListener.key
        jobMap.run {
            if (containsKey(key)) {
                this[key]?.forEach { it.cancel() }
                        ?.also { clear() }
                remove(key)
            }
        }
        requestMap.remove(key)
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

//    private infix fun ResultListener.exceptionHandlerFor(requestType: RequestType): CoroutineExceptionHandler {
//        return CoroutineExceptionHandler { _, throwable ->
//            val jobb = jobMap[key]
//            sendErrorFor(requestType, throwable)
//        }
//    }

    private infix fun ResultListener.add(requestType: RequestType) {
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

    private fun ResultListener.sendErrorFor(requestType: RequestType, throwable: Throwable) {
        changeRequestStatus(this, requestType, Status.Error)
        onRequestError(requestType, throwable)
    }

    private fun <K, V> concurrentMapOf(vararg pair: Pair<K, V>): ConcurrentHashMap<K, V> {
        val map = ConcurrentHashMap<K, V>()
        pair.forEach {
            map[it.first] = it.second
        }
        return map
    }
}