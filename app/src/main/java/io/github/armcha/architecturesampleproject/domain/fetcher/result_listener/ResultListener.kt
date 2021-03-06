package io.github.armcha.architecturesampleproject.domain.fetcher.result_listener

interface ResultListener {

    fun onRequestStart(requestType: RequestType) {}

    fun onRequestError(requestType: RequestType, throwable: Throwable) {}
}