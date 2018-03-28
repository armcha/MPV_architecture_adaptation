package io.github.armcha.architecturesampleproject.di.component


import dagger.Subcomponent
import io.github.armcha.architecturesampleproject.di.module.FragmentModule
import io.github.armcha.architecturesampleproject.di.scope.PerFragment
import io.github.armcha.architecturesampleproject.ui.fragment.MainFragment
import io.github.armcha.architecturesampleproject.ui.fragment.SecondFragment

@PerFragment
@Subcomponent(modules = [(FragmentModule::class)])
interface FragmentComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): FragmentComponent
    }

    fun inject(mainFragment: MainFragment)

    fun inject(secondFragment: SecondFragment)
}