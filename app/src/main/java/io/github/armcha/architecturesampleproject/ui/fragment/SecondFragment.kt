package io.github.armcha.architecturesampleproject.ui.fragment


import android.os.Bundle
import android.util.Log
import android.view.View
import io.github.armcha.architecturesampleproject.R
import io.github.armcha.architecturesampleproject.ui.base.BaseFragment


class SecondFragment : BaseFragment<SecondFragmentContract.View, SecondFragmentContract.Presenter>(), SecondFragmentContract.View {

    override val layoutResId = R.layout.second_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("SecondFragment", "SecondFragment ${presenter.hashCode()}")
    }

    override fun inject() {
        fragmentComponent.inject(this)
    }

}
