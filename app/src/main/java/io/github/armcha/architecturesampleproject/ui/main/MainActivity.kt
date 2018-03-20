package io.github.armcha.architecturesampleproject.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import io.github.armcha.architecturesampleproject.R
import io.github.armcha.architecturesampleproject.domain.model.User
import io.github.armcha.architecturesampleproject.ui.base.BaseActivity
import io.github.armcha.architecturesampleproject.ui.second.SecondActivity
import javax.inject.Inject

class MainActivity : BaseActivity<MainActivityContract.View, MainActivityContract.Presenter>(),
        MainActivityContract.View {

    @Inject
    lateinit var mainPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("presenter", "presenter ${presenter.hashCode()}")
        Log.e("presenter", "presenter ${mainPresenter.hashCode()}")

        findViewById<View>(R.id.firstButton).setOnClickListener {
            presenter.doSomethingHeavy()
        }
        findViewById<View>(R.id.secondButton).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

    override fun onDestroy() {
        Log.e("MainActivity", "" + isFinishing + "")
        Log.e("MainActivity", "" + isChangingConfigurations + "")
        super.onDestroy()
    }

    override fun showSomething(user: User) {
        Toast.makeText(this, user.name, Toast.LENGTH_SHORT).show()
    }

    override fun initPresenter(): MainActivityContract.Presenter {
        return mainPresenter
    }

    override fun inject() {
        activityComponent.inject(this)
    }
}
