package io.github.armcha.architecturesampleproject.ui.base

import android.os.Bundle
import io.armcha.arch.BaseMVPActivity
import io.github.armcha.architecturesampleproject.App
import io.github.armcha.architecturesampleproject.di.component.ActivityComponent
import io.github.armcha.architecturesampleproject.di.module.ActivityModule

abstract class BaseActivity<V : BaseContract.View, out P : BaseContract.Presenter<V>>
    : BaseMVPActivity<V, P>(), BaseContract.View {

    //protected abstract val presenter: P

    val activityComponent: ActivityComponent by lazy {
        App.applicationComponent + ActivityModule(this)
    }

    abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
       // App.createPresenterComponent()
        inject()
        super.onCreate(savedInstanceState)
    }

//    override fun initPresenter(): P {
//        return presenter
//    }
}
