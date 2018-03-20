package io.github.armcha.architecturesampleproject.ui.second

import android.os.Bundle
import io.github.armcha.architecturesampleproject.R
import io.github.armcha.architecturesampleproject.ui.base.BaseActivity
import javax.inject.Inject

class SecondActivity : BaseActivity<SecondContract.View, SecondContract.Presenter>(),
        SecondContract.View {

    @Inject
    lateinit var secondPresenter: SecondPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }

    override fun initPresenter(): SecondContract.Presenter {
        return secondPresenter
    }

    override fun inject() {
        activityComponent?.inject(this)
    }
}
