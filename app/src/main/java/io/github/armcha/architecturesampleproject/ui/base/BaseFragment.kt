package io.github.armcha.architecturesampleproject.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.armcha.arch.BaseMVPFragment
import io.github.armcha.architecturesampleproject.App
import io.github.armcha.architecturesampleproject.di.component.ActivityComponent
import io.github.armcha.architecturesampleproject.di.component.FragmentComponent
import io.github.armcha.architecturesampleproject.di.component.ScreenComponent
import io.github.armcha.architecturesampleproject.di.module.FragmentModule
import io.github.armcha.architecturesampleproject.di.module.ScreenModule

abstract class BaseFragment<V : BaseContract.View, out P : BaseContract.Presenter<V>>
    : BaseMVPFragment<V, P>(), BaseContract.View {

    protected abstract val layoutResId: Int

    protected lateinit var fragmentComponent: FragmentComponent
    protected lateinit var activityComponent: ActivityComponent

    abstract fun inject()

    override fun insertStoreObject(): Any? {
        return App.applicationComponent + ScreenModule()
    }

    override fun onDetach() {
        super.onDetach()
        if(isRemoving){

        }
    }

    override fun onStoredObjectReady(storedObject: Any?) {
        val base = activity as BaseActivity<*, *>
        fragmentComponent = base.activityComponent + FragmentModule()
        inject()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResId, container, false)
    }
}