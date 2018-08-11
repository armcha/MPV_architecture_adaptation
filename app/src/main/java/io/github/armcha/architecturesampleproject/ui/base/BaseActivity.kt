package io.github.armcha.architecturesampleproject.ui.base

import io.armcha.arch.BaseMVPActivity
import io.github.armcha.architecturesampleproject.App
import io.github.armcha.architecturesampleproject.di.component.ActivityComponent
import io.github.armcha.architecturesampleproject.di.component.ScreenComponent
import io.github.armcha.architecturesampleproject.ui.second.SecondContract
import javax.inject.Inject

abstract class BaseActivity<V : BaseContract.View, P : BaseContract.Presenter<V>>
    : BaseMVPActivity<V, P>(), BaseContract.View {

    @Inject
    override lateinit var presenter: P

    lateinit var activityComponent: ActivityComponent

    abstract fun inject()

    override fun insertStoreObject(): Any? {
        return App.applicationComponent
                .screenBuilder()
                .build()
    }

    override fun onStoredObjectReady(storedObject: Any?) {
        val screenComponent = storedObject as ScreenComponent
        activityComponent = screenComponent
                .activityComponentBuilder()
                .activity(this)
                .build()
        inject()
    }
}
