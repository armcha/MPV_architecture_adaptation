package io.armcha.arch

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class BaseViewModelFactory<P> : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BaseViewModel<P>() as T
    }
}