package io.github.armcha.architecturesampleproject.ui.base

import android.os.Bundle
import io.armcha.arch.BaseMVPActivity
import io.github.armcha.architecturesampleproject.App
import io.github.armcha.architecturesampleproject.di.component.ScreenComponent
import io.github.armcha.architecturesampleproject.di.module.ActivityModule
import io.github.armcha.architecturesampleproject.di.module.ScreenModule

abstract class BaseActivity<V : BaseContract.View, P : BaseContract.Presenter<V>>
    : BaseMVPActivity<V, P>(), BaseContract.View {

    protected abstract val presenter: P

    // private lateinit var holder: HolderRetainFragment

    private var screenComponent: ScreenComponent? = null

    val activityComponent by lazy {
        screenComponent!! + ActivityModule(this)
    }

    abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (supportFragmentManager.findFragmentByTag("TAG123") == null) {
//            holder = HolderRetainFragment()
//            supportFragmentManager.beginTransaction().add(holder, "TAG123").commit()
//        } else {
//            holder = supportFragmentManager.findFragmentByTag("TAG123") as HolderRetainFragment
//        }
        screenComponent = lastCustomNonConfigurationInstance as? ScreenComponent
                ?: App.applicationComponent + ScreenModule()
        inject()
        createPresenter()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return screenComponent ?: super.onRetainCustomNonConfigurationInstance()
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            screenComponent = null
        }
    }

    override fun initPresenter(): P {
        return presenter
    }
}
