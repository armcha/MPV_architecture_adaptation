package io.armcha.arch

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseMVPActivity<V : BaseMVPContract.View, out P : BaseMVPContract.Presenter<V>>
    : AppCompatActivity(), BaseMVPContract.View, BaseViewModel.ClearCallBack {

    private val factory = BaseViewModelFactory()
    private lateinit var secondBaseViewModel: BaseViewModel
    private var storedObject: Any? = null
    abstract val presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        // Log.e("onCreate", "storedObject hashcode ${storedObject?.hashCode()}")
        onStoredObjectReady(storedObject)
        presenter.attachLifecycle(lifecycle)
        presenter.attachView(this as V)
        if (isFirstCreation) {
            presenter.onPresenterCreate()
        }
    }

    override fun onCleared() {
        //Log.e("onCleared", "onCleared BaseActivity")
        presenter.onPresenterDestroy()
    }

    abstract fun onStoredObjectReady(storedObject: Any?)

    override fun onDestroy() {
        presenter.detachLifecycle(lifecycle)
        presenter.detachView()
        super.onDestroy()
        secondBaseViewModel.clearCallBack = null
    }

    abstract fun insertStoreObject(): Any?
}

