package io.github.armcha.architecturesampleproject.ui.base

import android.os.Bundle
import io.armcha.arch.BaseMVPActivity
import io.github.armcha.architecturesampleproject.App
import io.github.armcha.architecturesampleproject.di.component.ActivityComponent
import io.github.armcha.architecturesampleproject.di.component.ScreenComponent
import io.github.armcha.architecturesampleproject.di.module.ActivityModule
import io.github.armcha.architecturesampleproject.di.module.ScreenModule

abstract class BaseActivity<V : BaseContract.View, out P : BaseContract.Presenter<V>>
    : BaseMVPActivity<V, P>(), BaseContract.View {

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
