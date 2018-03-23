package io.github.armcha.architecturesampleproject.ui.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.armcha.arch.BaseMVPFragment
import io.github.armcha.architecturesampleproject.di.component.ScreenComponent
import io.github.armcha.architecturesampleproject.di.module.FragmentModule

abstract class BaseFragment<V : BaseContract.View, out P : BaseContract.Presenter<V>>
    : BaseMVPFragment<V, P>(), BaseContract.View {

    protected abstract val layoutResId: Int

    protected abstract val presenter: P

    private var screenComponent: ScreenComponent? = null

//    private val fragmentComponent by lazy {
//        holder.screenComponent + FragmentModule()
//    }

    private lateinit var activity: BaseActivity<*, *>

    private lateinit var holder: HolderRetainFragment

    @CallSuper
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            activity = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val supportFragmentManager = activity.getSupportFragmentManager()
        if (supportFragmentManager.findFragmentByTag("TAG123") == null) {
            holder = HolderRetainFragment()
            supportFragmentManager.beginTransaction().add(holder, "TAG123").commit()
        } else {
            holder = supportFragmentManager.findFragmentByTag("TAG123") as HolderRetainFragment
        }
    }

    override fun initPresenter(): P {
        return presenter
    }
}