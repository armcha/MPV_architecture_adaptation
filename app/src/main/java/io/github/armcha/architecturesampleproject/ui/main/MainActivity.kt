package io.github.armcha.architecturesampleproject.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.AsyncLayoutInflater
import android.util.Log
import android.view.View
import android.widget.Toast
import io.github.armcha.architecturesampleproject.App
import io.github.armcha.architecturesampleproject.R
import io.github.armcha.architecturesampleproject.domain.model.User
import io.github.armcha.architecturesampleproject.ui.base.BaseActivity
import io.github.armcha.architecturesampleproject.ui.fragment.MainFragment
import io.github.armcha.architecturesampleproject.ui.fragment.SecondFragment
import io.github.armcha.architecturesampleproject.ui.second.SecondActivity
import javax.inject.Inject

class MainActivity : BaseActivity<MainActivityContract.View, MainActivityContract.Presenter>(),
        MainActivityContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("presenter", "presenter ${presenter.hashCode()}")

        findViewById<View>(R.id.firstButton).setOnClickListener {
            presenter.doSomethingHeavy()
        }
        findViewById<View>(R.id.secondButton).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

    override fun openFragment() {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, MainFragment())
                .commit()
        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, SecondFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun showSomething(user: User) {
        Toast.makeText(this, user.name, Toast.LENGTH_SHORT).show()
    }

    override fun inject() {
        activityComponent.inject(this)
    }
}
