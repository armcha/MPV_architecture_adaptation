package io.github.armcha.architecturesampleproject.di.component


import androidx.appcompat.app.AppCompatActivity
import dagger.BindsInstance
import dagger.Subcomponent
import io.github.armcha.architecturesampleproject.di.module.ActivityModule
import io.github.armcha.architecturesampleproject.di.scope.PerActivity
import io.github.armcha.architecturesampleproject.ui.coroutine_presenter.CoroutineActivity
import io.github.armcha.architecturesampleproject.ui.main.MainActivity
import io.github.armcha.architecturesampleproject.ui.presenter_with_livedata.ThirdActivity
import io.github.armcha.architecturesampleproject.ui.second.SecondActivity

@PerActivity
@Subcomponent(modules = [ActivityModule::class])
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

    fun inject(thirdActivity: ThirdActivity)

    fun inject(coroutineActivity: CoroutineActivity)
}