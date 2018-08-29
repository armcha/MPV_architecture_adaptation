package io.armcha.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

abstract class BaseMVPFragment<V : BaseMVPContract.View, out P : BaseMVPContract.Presenter<V>, O>
    : Fragment(), BaseMVPContract.View, BaseViewModel.ClearCallBack {

    protected abstract val presenter: P

    private val delegate = ViewModelDelegate()

    abstract fun onStoredObjectReady(storedObject: O)
    abstract fun insertStoreObject(): O

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        delegate.create(this, ::insertStoreObject)
        onStoredObjectReady(delegate.storedObject as O)
        presenter.attachLifecycle(lifecycle)
        presenter.attachView(this as V)
    }

    override fun onResume() {
        super.onResume()
        delegate.onResume(presenter)
    }

    override fun onDestroy() {
        presenter.detachLifecycle(lifecycle)
        presenter.detachView()
        super.onDestroy()
        delegate.onDestroy()
    }

    final override fun onCleared() {
        presenter.onPresenterDestroy()
    }
}
