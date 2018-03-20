package io.armcha.arch

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class BaseViewModelFactory<V : BaseMVPContract.View, P : BaseMVPContract.Presenter<V>> : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BaseViewModel<V, P>() as T
    }
}