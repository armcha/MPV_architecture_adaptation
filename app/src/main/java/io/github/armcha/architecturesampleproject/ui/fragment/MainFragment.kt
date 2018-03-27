package io.github.armcha.architecturesampleproject.ui.fragment


import android.os.Bundle
import android.util.Log
import android.view.View
import io.github.armcha.architecturesampleproject.R
import io.github.armcha.architecturesampleproject.ui.base.BaseFragment
import javax.inject.Inject


class MainFragment : BaseFragment<MainFragmentContract.View, MainFragmentContract.Presenter>(), MainFragmentContract.View {

    override val layoutResId = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("MainFragment", "MainFragment ${presenter.hashCode()}")
    }

    override fun inject() {
        fragmentComponent.inject(this)
    }
}
