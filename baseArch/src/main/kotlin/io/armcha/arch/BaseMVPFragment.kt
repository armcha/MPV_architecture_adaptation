package io.armcha.arch

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View

abstract class BaseMVPFragment<V : BaseMVPContract.View, out P : BaseMVPContract.Presenter<V>>
    : Fragment(), BaseMVPContract.View, BaseViewModel.ClearCallBack {

    private val factory = BaseViewModelFactory()
    private lateinit var secondBaseViewModel: BaseViewModel
    private var storedObject: Any? = null
    protected abstract val presenter: P

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        secondBaseViewModel = ViewModelProviders
                .of(this, factory)
                .get(BaseViewModel::class.java)
        var isFirstCreation = false
        if (!secondBaseViewModel.hasStoredObject()) {
            isFirstCreation = true
            secondBaseViewModel.storeObject(insertStoreObject())
        }
        secondBaseViewModel.clearCallBack = this
        storedObject = secondBaseViewModel.storedObject
        Log.e("onCreate", "storedObject hashcode ${storedObject?.hashCode()}")
        onStoredObjectReady(storedObject)
        presenter.attachLifecycle(lifecycle)
        presenter.attachView(this as V)
        if (isFirstCreation) {
            presenter.onPresenterCreate()
        }
    }

    abstract fun onStoredObjectReady(storedObject: Any?)

    override fun onCleared() {
        presenter.onPresenterDestroy()
    }

    override fun onDestroy() {
        secondBaseViewModel.clearCallBack = null
        presenter.detachLifecycle(lifecycle)
        presenter.detachView()
        super.onDestroy()
    }

    abstract fun insertStoreObject(): Any?

}
