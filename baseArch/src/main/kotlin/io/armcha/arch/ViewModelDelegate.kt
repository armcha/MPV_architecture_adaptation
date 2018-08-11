package io.armcha.arch

import android.arch.lifecycle.ViewModelProviders
import android.util.Log

private typealias Presenter = BaseMVPContract.Presenter<*>
private typealias Activity = BaseMVPActivity<*, *>
private typealias Fragment = BaseMVPFragment<*, *>

class ViewModelDelegate {

    var storedObject: Any? = null

    private var isFirstCreation = false
    private val factory = BaseViewModelFactory()
    private lateinit var viewModel: BaseViewModel

    fun create(activity: Activity, insertStoreObject: () -> Any?) {
        internalCreate(activity, insertStoreObject = insertStoreObject)
    }

    fun create(fragment: Fragment, insertStoreObject: () -> Any?) {
        internalCreate(fragment = fragment, insertStoreObject = insertStoreObject)
    }

    fun onResume(presenter: Presenter) {
        if (isFirstCreation) {
            presenter.onPresenterCreate()
            isFirstCreation = false
        }
    }

    fun onDestroy() {
        viewModel.clearCallBack = null
    }

    private fun internalCreate(activity: Activity? = null, fragment: Fragment? = null,
                               insertStoreObject: () -> Any?) {

        val viewModelProvider = if (activity != null) {
            ViewModelProviders.of(activity, factory)
        } else {
            ViewModelProviders.of(fragment!!, factory)
        }
        viewModel = viewModelProvider.get(BaseViewModel::class.java)
        viewModel.clearCallBack = activity ?: fragment
        if (!viewModel.hasStoredObject()) {
            isFirstCreation = true
            viewModel.storeObject(insertStoreObject())
        }
        storedObject = viewModel.storedObject
        Log.e("storedObject", "${storedObject!!::class.java.simpleName} hashcode ${storedObject?.hashCode()}")
    }
}