package io.armcha.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class BaseViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BaseViewModel() as T
    }
}