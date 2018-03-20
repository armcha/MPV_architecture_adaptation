package io.github.armcha.architecturesampleproject.ui.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.armcha.arch.BaseMVPFragment

abstract class BaseFragment<V : BaseContract.View, out P : BaseContract.Presenter<V>>
    : BaseMVPFragment<V, P>(), BaseContract.View {

    protected abstract val layoutResId: Int

    protected abstract val presenter: P

    @CallSuper
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            context.inject()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun initPresenter(): P {
        return presenter
    }
}