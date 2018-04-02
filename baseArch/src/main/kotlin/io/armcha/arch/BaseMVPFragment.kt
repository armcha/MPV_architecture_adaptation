package io.armcha.arch

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View

abstract class BaseMVPFragment<V : BaseMVPContract.View, out P : BaseMVPContract.Presenter<V>>
    : Fragment(), BaseMVPContract.View, BaseViewModel.ClearCallBack {

    protected abstract val presenter: P
    private lateinit var secondBaseViewModel: BaseViewModel
    private val factory = BaseViewModelFactory()
    private var storedObject: Any? = null
    private var isFirstCreation = false

    abstract fun onStoredObjectReady(storedObject: Any?)
    abstract fun insertStoreObject(): Any?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        secondBaseViewModel = ViewModelProviders
                .of(this, factory)
                .get(BaseViewModel::class.java)
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
    }

    override fun onResume() {
        super.onResume()
        if (isFirstCreation) {
            presenter.onPresenterCreate()
            isFirstCreation = false
        }
    }

    override fun onDestroy() {
        secondBaseViewModel.clearCallBack = null
        presenter.detachLifecycle(lifecycle)
        presenter.detachView()
        super.onDestroy()
    }

    override fun onCleared() {
        presenter.onPresenterDestroy()
    }
}
