package io.armcha.arch

import android.arch.lifecycle.ViewModel
import android.util.Log

class BaseViewModel<V : BaseMVPContract.View, P : BaseMVPContract.Presenter<V>> : ViewModel() {

    var presenter: P? = null
        set(value) {
            if (field == null) {
                field = value
            }
        }

    override fun onCleared() {
        Log.e("onCleared", "${presenter?.javaClass?.simpleName} is going to be destroyed")
        presenter?.onPresenterDestroy()
        presenter = null
        super.onCleared()
    }
}
