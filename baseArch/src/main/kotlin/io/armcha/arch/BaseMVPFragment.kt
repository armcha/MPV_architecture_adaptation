package io.armcha.arch

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View

abstract class BaseMVPFragment<V : BaseMVPContract.View, out P : BaseMVPContract.Presenter<V>>
    : Fragment(), BaseMVPContract.View, BaseViewModel.ClearCallBack {

    protected abstract val presenter: P

    private val delegate = ViewModelDelegate()

    abstract fun onStoredObjectReady(storedObject: Any?)
    abstract fun insertStoreObject(): Any?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        delegate.create(this, this::insertStoreObject)
        onStoredObjectReady(delegate.storedObject)
        presenter.attachLifecycle(lifecycle)
        presenter.attachView(this as V)
    }

    override fun onResume() {
        super.onResume()
        delegate.onResume(presenter)
    }

    override fun onDestroy() {
        delegate.onDestroy()
        presenter.detachLifecycle(lifecycle)
        presenter.detachView()
        super.onDestroy()
    }

    final override fun onCleared() {
        presenter.onPresenterDestroy()
    }
}
