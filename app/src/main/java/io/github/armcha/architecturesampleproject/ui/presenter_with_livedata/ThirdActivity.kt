package io.github.armcha.architecturesampleproject.ui.presenter_with_livedata

import android.os.Bundle
import android.util.Log
import io.github.armcha.architecturesampleproject.R
import io.github.armcha.architecturesampleproject.domain.model.Event
import io.github.armcha.architecturesampleproject.ui.base.BaseActivity

class ThirdActivity : BaseActivity<ThirdActivityContract.View, ThirdActivityContract.Presenter>(), ThirdActivityContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Log.e("presenter", "presenter ${presenter.hashCode()}")
    }

    override fun showEvents(eventList: List<Event>) {
        Log.e("showEvents", "showEvents ${eventList.size}")
    }

    override fun showEventsLoading() {
        Log.e("showEventsLoading", "showEventsLoading")
    }

    override fun showEventsLoadError() {
        Log.e("showEventsLoadError", "showEventsLoadError")
    }

    override fun inject() {
        activityComponent.inject(this)
    }
}
