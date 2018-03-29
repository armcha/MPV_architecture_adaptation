package io.armcha.arch

import android.arch.lifecycle.ViewModel
import android.util.Log

class BaseViewModel : ViewModel() {

    interface ClearCallBack {
        fun onCleared()
    }

    var clearCallBack: ClearCallBack? = null
    var storedObject: Any? = null

    fun storeObject(any: Any?) {
        storedObject = any
        val aaa = storedObject
        Log.e("storeObject", "Stored object ${aaa?.javaClass?.simpleName} with hashcode ${aaa?.hashCode()}")
    }

    fun hasStoredObject() = storedObject != null

    override fun onCleared() {
       // Log.e("clearCallBack", "clearCallBack $clearCallBack")
        clearCallBack?.onCleared()
        val any = storedObject as Any
        Log.e("onCleared", "onCleared object ${any.javaClass.simpleName} with hashcode ${any.hashCode()}")
        storedObject = null
        super.onCleared()
    }
}
