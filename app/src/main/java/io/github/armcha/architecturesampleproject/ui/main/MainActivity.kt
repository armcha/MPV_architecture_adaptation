package io.github.armcha.architecturesampleproject.ui.main

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import io.armcha.arch.եթե
import io.armcha.arch.հակառակԴեպքում
import io.github.armcha.architecturesampleproject.R
import io.github.armcha.architecturesampleproject.domain.model.User
import io.github.armcha.architecturesampleproject.ui.base.BaseActivity
import io.github.armcha.architecturesampleproject.ui.fragment.MainFragment
import io.github.armcha.architecturesampleproject.ui.second.SecondActivity
import io.github.armcha.architecturesampleproject.ui.util.NonNullObserver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainActivityContract.View, MainActivityPresenter>(),
        MainActivityContract.View {

    private var loadingHandler: Handler? = null
    private var runnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstButton.setOnClickListener {
            presenter.saveUser("Jack", "Wharton")
        }
        secondButton.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingHandler?.removeCallbacks(runnable)
    }

    override fun openFragment() {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, MainFragment())
                .commit()

//        supportFragmentManager.beginTransaction()
//                .add(R.id.fragmentContainer, SecondFragment())
//                .addToBackStack(null)
//                .commit()
    }

    override fun showUsers(userList: List<User>) {
        loadingHandler?.removeCallbacks(runnable)
        runnable = null
        userStatus.text = null
        loading.text = null
        userList.forEach {
            userStatus.append("$it\n")
        }
    }

    override fun showLoadUserError() {

    }

    override fun showUserSaved() {
        Toast.makeText(this, "USER SAVED", Toast.LENGTH_SHORT).show()
    }

    override fun inject() {
        activityComponent.inject(this)
    }

    override fun showUsersLoading() {
        loadingHandler = Handler()
        load()
    }

    private fun load() {
        runnable = Runnable {
            loading.text = addDot(loading.text)
            load()
        }
        loadingHandler?.postDelayed(runnable, 150)
    }

    private fun addDot(current: CharSequence): String {
        val count = current.count()
        if (count < 15) {
            return buildString {
                repeat(count / 2 + 1) {
                    append(". ")
                }
            }
        }
        return ""
    }
}
