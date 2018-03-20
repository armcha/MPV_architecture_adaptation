package io.github.armcha.architecturesampleproject.domain.fetcher

sealed class Status {

    object Loading : Status()
    object Error : Status()
    object Success : Status()
    object EmptySuccess : Status()
    object Idle : Status()
}