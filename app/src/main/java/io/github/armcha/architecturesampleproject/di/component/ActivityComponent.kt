package io.github.armcha.architecturesampleproject.di.component


import android.app.Application
import android.support.v7.app.AppCompatActivity
import dagger.BindsInstance
import dagger.Component
import io.github.armcha.architecturesampleproject.di.scope.PerActivity
import dagger.Subcomponent
import io.github.armcha.architecturesampleproject.ui.main.MainActivity
import io.github.armcha.architecturesampleproject.di.module.ActivityModule
import io.github.armcha.architecturesampleproject.di.module.ApplicationModule
import io.github.armcha.architecturesampleproject.di.module.FragmentModule
import io.github.armcha.architecturesampleproject.ui.second.SecondActivity

@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun activity(appCompatActivity: AppCompatActivity): Builder

        fun build(): ActivityComponent
    }

    fun fragmentComponentBuilder(): FragmentComponent.Builder

    fun inject(mainActivity: MainActivity)

    fun inject(secondActivity: SecondActivity)
}