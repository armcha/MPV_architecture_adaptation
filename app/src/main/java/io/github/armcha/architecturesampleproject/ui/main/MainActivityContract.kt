package io.github.armcha.architecturesampleproject.ui.main

import io.github.armcha.architecturesampleproject.domain.model.User
import io.github.armcha.architecturesampleproject.ui.base.BaseContract


interface MainActivityContract {

    interface View : BaseContract.View {

        fun showUsers(userList: List<User>)

        fun showUserSaved()

        fun showUsersLoading()

        fun showLoadUserError()
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun saveUser(name: String, userName: String)
    }
}