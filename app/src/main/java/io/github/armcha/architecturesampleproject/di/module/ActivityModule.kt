package io.github.armcha.architecturesampleproject.di.module

import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import dagger.Module
import dagger.Provides
import io.github.armcha.architecturesampleproject.di.component.FragmentComponent
import io.github.armcha.architecturesampleproject.di.scope.PerActivity

@Module
class ActivityModule {

    @PerActivity
    @Provides
    fun providesLayoutInflater(activity: AppCompatActivity): LayoutInflater =
            LayoutInflater.from(activity)

    @PerActivity
    @Provides
    fun providesFragmentManager(activity: AppCompatActivity): androidx.fragment.app.FragmentManager =
            activity.supportFragmentManager
}