package io.github.armcha.architecturesampleproject.ui.base

import android.support.v4.app.Fragment
import io.github.armcha.architecturesampleproject.App
import io.github.armcha.architecturesampleproject.di.module.ScreenModule

class HolderRetainFragment : Fragment() {

    val screenComponent by lazy {
        App.applicationComponent + ScreenModule()
    }

    init {
        retainInstance = true
    }
}