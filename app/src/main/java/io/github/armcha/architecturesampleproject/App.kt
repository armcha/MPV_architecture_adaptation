package io.github.armcha.architecturesampleproject

import android.app.Application
import com.squareup.leakcanary.LeakCanary
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
                .application(this)
                .build()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }
}