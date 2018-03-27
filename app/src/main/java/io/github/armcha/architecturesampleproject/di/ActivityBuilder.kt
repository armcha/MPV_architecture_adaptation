package io.github.armcha.architecturesampleproject.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.armcha.architecturesampleproject.di.module.ActivityModule
import io.github.armcha.architecturesampleproject.di.scope.PerActivity
import io.github.armcha.architecturesampleproject.ui.main.MainActivity
import io.github.armcha.architecturesampleproject.ui.second.SecondActivity


/**
 * NeverForget
 *
 * Created by Arman Chatikyan on 24 Nov 2017
 * Company - Volo LLC
 */

//@Module
//interface ActivityBuilder {
//
//    @PerActivity
//    @ContributesAndroidInjector(modules = [ActivityModule::class])
//    fun bindMainActivity(): MainActivity
//
//    @PerActivity
//    @ContributesAndroidInjector(modules = [ActivityModule::class])
//    fun bindSecondActivity(): SecondActivity
//
////    @PerActivity
////    @Binds
////    fun provideActivity(activity: BaseActivity): AppCompatActivity
//}