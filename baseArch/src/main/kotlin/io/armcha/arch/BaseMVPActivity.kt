package io.armcha.arch

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import android.util.Log

abstract class BaseMVPActivity<V : BaseMVPContract.View, out P : BaseMVPContract.Presenter<V>>
    : AppCompatActivity(), BaseMVPContract.View, BaseViewModel.ClearCallBack {

    abstract val presenter: P

    private lateinit var secondBaseViewModel: BaseViewModel
    private val factory = BaseViewModelFactory()
    private var storedObject: Any? = null
    private var isFirstCreation = false

    abstract fun onStoredObjectReady(storedObject: Any?)
    abstract fun insertStoreObject(): Any?

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    @CallSuper
    override fun onResume() {
        super.onResume()
        if (isFirstCreation) {
            presenter.onPresenterCreate()
            isFirstCreation = false
        }
    }

    @CallSuper
    override fun onDestroy() {
        presenter.detachLifecycle(lifecycle)
        presenter.detachView()
        super.onDestroy()
        secondBaseViewModel.clearCallBack = null
    }

    final override fun onCleared() {
        presenter.onPresenterDestroy()
    }
}

