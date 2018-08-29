package io.github.armcha.architecturesampleproject.ui.coroutine_presenter

import android.os.Bundle
import android.util.Log
import io.github.armcha.architecturesampleproject.R
import io.github.armcha.architecturesampleproject.domain.model.Phone
import io.github.armcha.architecturesampleproject.ui.base.BaseActivity

class CoroutineActivity : BaseActivity<CoroutineActivityContract.View, CoroutineActivityContract.Presenter>(), CoroutineActivityContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
        Log.e("presenter", "presenter ${presenter.hashCode()}")
    }

    override fun showPhones(phoneList: List<Phone>) {
        Log.e("showPhones", "showPhones ${phoneList.size}")
    }

    override fun showPhonesLoading() {
        Log.e("showPhonesLoading", "showPhonesLoading")
    }

    override fun showPhonesLoadError() {
        Log.e("showPhonesLoadError", "showPhonesLoadError")
    }

    override fun inject() {
        activityComponent.inject(this)
    }
}
