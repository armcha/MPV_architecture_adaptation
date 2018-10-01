package io.github.armcha.architecturesampleproject.ui.second

import android.os.Bundle
import android.util.Log
import io.github.armcha.architecturesampleproject.R
import io.github.armcha.architecturesampleproject.ui.base.BaseActivity


class SecondActivity : BaseActivity<SecondContract.View, SecondPresenter>(), SecondContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Log.e("presenter", "presenter ${presenter.hashCode()}")
    }

    override fun inject() {
        activityComponent.inject(this)
    }
}
