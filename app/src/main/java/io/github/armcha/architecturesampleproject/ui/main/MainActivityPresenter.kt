package io.github.armcha.architecturesampleproject.ui.main

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.fetcher.Status
import io.github.armcha.architecturesampleproject.domain.fetcher.result_listener.RequestType
import io.github.armcha.architecturesampleproject.domain.interactor.SecondInteractor
import io.github.armcha.architecturesampleproject.domain.interactor.SomeInteractor
import io.github.armcha.architecturesampleproject.domain.model.User
import io.github.armcha.architecturesampleproject.ui.base.BasePresenter
import javax.inject.Inject

@PerScreen
class MainActivityPresenter @Inject constructor(private val someInteractor: SomeInteractor,
                                                private val secondInteractor: SecondInteractor)
    : BasePresenter<MainActivityContract.View>(), MainActivityContract.Presenter {

    private var users = listOf<User>()

    @OnLifecycleEvent(value = Lifecycle.Event.ON_START)
    fun onStart() {
        Log.e("GET_USER", "STATUS IS ${GET_USER.status.javaClass.simpleName}")
        Log.e("SAVE_USER", "STATUS IS ${SAVE_USER.status.javaClass.simpleName}")
        when (GET_USER.status) {
            is Status.Loading -> view?.showUsersLoading()
            is Status.Error -> view?.showLoadUserError()
            is Status.Success -> view?.showUsers(users)
        }
    }

    override fun onPresenterCreate() {
        super.onPresenterCreate()
        fetch(someInteractor.getUser(), GET_USER) { users = it }
    }

    override fun saveUser(name: String, userName: String) {
        val user = User(name, userName)
        complete(someInteractor.saveUser(user), SAVE_USER) {
            view?.showUserSaved()
        }
    }

    override fun onRequestStart(requestType: RequestType) {
        if (requestType == GET_USER) {
            view?.showUsersLoading()
        }
    }

    override fun onRequestError(requestType: RequestType, throwable: Throwable) {
        when (requestType) {
            GET_USER -> view?.showLoadUserError()
            SAVE_USER -> TODO() //show save user error
            else -> TODO() // show some general error
        }
    }
}