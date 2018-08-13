package io.github.armcha.architecturesampleproject.ui.coroutine_presenter

import io.github.armcha.architecturesampleproject.domain.model.Event
import io.github.armcha.architecturesampleproject.domain.model.Phone
import io.github.armcha.architecturesampleproject.domain.model.User
import io.github.armcha.architecturesampleproject.ui.base.BaseContract


interface CoroutineActivityContract {

    interface View : BaseContract.View {

        fun showPhones(phoneList: List<Phone>)

        fun showPhonesLoading()

        fun showPhonesLoadError()
    }

    interface Presenter : BaseContract.Presenter<View> {


    }
}