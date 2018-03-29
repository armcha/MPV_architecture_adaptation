package io.github.armcha.architecturesampleproject.ui.main

import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.fetcher.result_listener.RequestType
import io.github.armcha.architecturesampleproject.domain.interactor.SomeInteractor
import io.github.armcha.architecturesampleproject.domain.model.User
import io.github.armcha.architecturesampleproject.ui.base.BasePresenter
import javax.inject.Inject

@PerScreen
class MainActivityPresenter @Inject constructor(private val someInteractor: SomeInteractor)
    : BasePresenter<MainActivityContract.View>(), MainActivityContract.Presenter {

    override fun onPresenterCreate() {
        super.onPresenterCreate()
        //Log.e("PRESENTER", "onPresenterCreate")
        fetch(someInteractor.getUser(), RequestType.GET_USER, getUserSuccess)
        //view?.openFragment()
    }

    private val getUserSuccess = { user: User ->
      //  Log.e("PRESENTER", "getUserSuccess")
        view?.showSomething(user)
        Unit
    }

    fun a(){

    }

    override fun doSomethingHeavy() {
        //Log.e("PRESENTER", "DOING SOMETHING")
    }
}