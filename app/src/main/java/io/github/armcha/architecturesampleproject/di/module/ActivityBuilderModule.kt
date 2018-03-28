package io.github.armcha.architecturesampleproject.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.armcha.architecturesampleproject.di.component.FragmentComponent
import io.github.armcha.architecturesampleproject.di.scope.PerActivity
import io.github.armcha.architecturesampleproject.ui.main.MainActivity
import io.github.armcha.architecturesampleproject.ui.second.SecondActivity


/**
 * NeverForget
 *
 * Created by Arman Chatikyan on 27 Mar 2018
 * Company - Volo LLC
 */

//@Module
//interface ActivityBuilderModule {
//
//    @PerActivity
//    @ContributesAndroidInjector(modules = [ActivityModule::class])
//    fun bindMainActivity(): MainActivity
//
//    @PerActivity
//    @ContributesAndroidInjector(modules = [ActivityModule::class])
//    fun bindSecondActivity(): SecondActivity
//}