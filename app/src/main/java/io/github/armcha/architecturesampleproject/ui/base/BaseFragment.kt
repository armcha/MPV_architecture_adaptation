package io.github.armcha.architecturesampleproject.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.armcha.base.BaseMVPFragment
import io.github.armcha.architecturesampleproject.App
import io.github.armcha.architecturesampleproject.di.component.FragmentComponent
import javax.inject.Inject

abstract class BaseFragment<V : BaseContract.View, P : BaseContract.Presenter<V>>
    : BaseMVPFragment<V, P>(), BaseContract.View {

    protected abstract val layoutResId: Int

    @Inject
    override lateinit var presenter: P

    protected lateinit var fragmentComponent: FragmentComponent

    abstract fun inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun insertStoreObject(): Any? {
        return App.applicationComponent
                .screenBuilder()
                .build()
    }

    override fun onStoredObjectReady(storedObject: Any?) {
        val base = activity as BaseActivity<*, *>
        fragmentComponent = base.activityComponent
                .fragmentComponentBuilder()
                .build()
        inject()
    }
}