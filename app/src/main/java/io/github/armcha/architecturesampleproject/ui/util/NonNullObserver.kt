package io.github.armcha.architecturesampleproject.ui.util

import android.arch.lifecycle.Observer

class NonNullObserver<T>(private val onChange: (T) -> Unit) : Observer<T> {

    override fun onChanged(t: T?) {
        t?.let { onChange(it) }
    }
}