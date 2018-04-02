package io.github.armcha.architecturesampleproject.ui.main

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.fetcher.result_listener.RequestType
import io.github.armcha.architecturesampleproject.domain.interactor.SecondInteractor
import io.github.armcha.architecturesampleproject.domain.interactor.SomeInteractor
import io.github.armcha.architecturesampleproject.domain.model.User
import io.github.armcha.architecturesampleproject.ui.base.BasePresenter
import io.github.armcha.architecturesampleproject.ui.util.NonNullObserver
import javax.inject.Inject

@PerScreen
class MainActivityPresenter @Inject constructor(private val someInteractor: SomeInteractor,
                                                private val secondInteractor: SecondInteractor)
    : BasePresenter<MainActivityContract.View>(), MainActivityContract.Presenter {

    private val liveData = MutableLiveData<List<User>>()

    @OnLifecycleEvent(value = Lifecycle.Event.ON_START)
    fun onStart() {
        liveData.observe(this, NonNullObserver {
            Log.e("liveData", "$it")
            view?.showUsers(it)
        })
        when {
            GET_USER statusIs LOADING -> view?.showUsersLoading()
            GET_USER statusIs ERROR -> view?.showLoadUserError()
        }
    }

    override fun onPresenterCreate() {
        super.onPresenterCreate()
        fetch(someInteractor.getUser(), GET_USER) { liveData.value = it }
        view?.openFragment()
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