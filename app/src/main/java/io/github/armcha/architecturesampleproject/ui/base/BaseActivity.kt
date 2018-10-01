package io.github.armcha.architecturesampleproject.ui.base

import io.armcha.base.BaseMVPActivity
import io.github.armcha.architecturesampleproject.App
import io.github.armcha.architecturesampleproject.di.component.ActivityComponent
import io.github.armcha.architecturesampleproject.di.component.ScreenComponent
import javax.inject.Inject

abstract class BaseActivity<V : BaseContract.View, P : BaseContract.Presenter<V>>
    : BaseMVPActivity<V, P, ScreenComponent>(), BaseContract.View {

    @Inject
    override lateinit var presenter: P

    lateinit var activityComponent: ActivityComponent

    abstract fun inject()

    final override fun insertStoreObject(): ScreenComponent {
        return App.applicationComponent
            .screenBuilder()
            .build()
    }

    final override fun onStoredObjectReady(storedObject: ScreenComponent) {
        activityComponent = storedObject.activityComponentBuilder()
            .activity(this)
            .build()
        inject()
    }
}
