package io.github.armcha.architecturesampleproject

import android.app.Application
import android.util.Log
import io.github.armcha.architecturesampleproject.di.component.ApplicationComponent
import io.github.armcha.architecturesampleproject.di.component.DaggerApplicationComponent
import io.github.armcha.architecturesampleproject.di.module.ApplicationModule

class App : Application() {

    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}