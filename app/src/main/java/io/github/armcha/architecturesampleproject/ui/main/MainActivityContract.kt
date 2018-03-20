package io.github.armcha.architecturesampleproject.ui.main

import io.github.armcha.architecturesampleproject.domain.model.User
import io.github.armcha.architecturesampleproject.ui.base.BaseContract


/**
 * NeverForget
 *
 * Created by Arman Chatikyan on 19 Mar 2018
 * Company - Volo LLC
 */

interface MainActivityContract {

    interface View : BaseContract.View {
        fun showSomething(user: User)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun doSomethingHeavy()
    }
}