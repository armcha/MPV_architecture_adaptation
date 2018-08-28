package io.armcha.base

import android.arch.lifecycle.ViewModel

class BaseViewModel : ViewModel() {

    var clearCallBack: ClearCallBack? = null
    var storedObject: Any? = null

    fun storeObject(any: Any?) {
        storedObject = any
    }

    fun hasStoredObject() = storedObject != null

    override fun onCleared() {
        clearCallBack?.onCleared()
        storedObject = null
        super.onCleared()
    }

    interface ClearCallBack {
        fun onCleared()
    }
}
