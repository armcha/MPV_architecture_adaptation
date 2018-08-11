package io.github.armcha.architecturesampleproject.ui.fragment


import android.os.Bundle
import android.util.Log
import android.view.View
import io.github.armcha.architecturesampleproject.R
import io.github.armcha.architecturesampleproject.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : BaseFragment<MainFragmentContract.View, MainFragmentContract.Presenter>(), MainFragmentContract.View {

    override val layoutResId = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("MainFragment", "MainFragment ${presenter.hashCode()}")

        openChildFragmentButton.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, SecondFragment())
                    .addToBackStack(SecondFragment::class.java.simpleName)
                    .commit()
        }
    }

    override fun inject() {
        fragmentComponent.inject(this)
    }
}
