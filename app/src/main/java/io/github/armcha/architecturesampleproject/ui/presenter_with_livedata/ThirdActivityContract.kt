package io.github.armcha.architecturesampleproject.ui.presenter_with_livedata

import io.github.armcha.architecturesampleproject.domain.model.Event
import io.github.armcha.architecturesampleproject.ui.base.BaseContract


interface ThirdActivityContract {

    interface View : BaseContract.View {

        fun showEvents(eventList: List<Event>)

        fun showEventsLoading()

        fun showEventsLoadError()
    }

    interface Presenter : BaseContract.Presenter<View> {


    }
}