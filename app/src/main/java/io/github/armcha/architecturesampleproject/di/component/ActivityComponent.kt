package io.github.armcha.architecturesampleproject.di.component


import io.github.armcha.architecturesampleproject.di.scope.PerActivity
import dagger.Subcomponent
import io.github.armcha.architecturesampleproject.ui.main.MainActivity
import io.github.armcha.architecturesampleproject.di.module.ActivityModule
import io.github.armcha.architecturesampleproject.ui.second.SecondActivity

@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(secondActivity: SecondActivity)

}