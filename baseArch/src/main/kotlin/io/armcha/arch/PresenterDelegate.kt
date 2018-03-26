//package io.armcha.arch
//
//import android.arch.lifecycle.Lifecycle
//import android.arch.lifecycle.ViewModelProviders
//import android.support.v4.app.Fragment
//import android.support.v7.app.AppCompatActivity
//
//class PresenterDelegate<V : BaseMVPContract.View, P : BaseMVPContract.Presenter<V>> {
//
//    private val factory = BaseViewModelFactory<V, P>()
//    lateinit var presenter: P
//
//    fun create(appCompatActivity: AppCompatActivity, createPresenter: () -> P) {
//        internalCreate(appCompatActivity, createPresenter = createPresenter)
//    }
//
//    fun create(fragment: Fragment, createPresenter: () -> P) {
//        internalCreate(fragment = fragment, createPresenter = createPresenter)
//    }
//
//    fun destroy(lifecycle: Lifecycle) {
//        presenter.detachLifecycle(lifecycle)
//        presenter.detachView()
//    }
//
//    @Suppress("UNCHECKED_CAST")
//    private fun internalCreate(appCompatActivity: AppCompatActivity? = null,
//                               fragment: Fragment? = null, createPresenter: () -> P) {
//        val (viewModelProvider, lifecycle) = if (appCompatActivity != null) {
//            ViewModelProviders.of(appCompatActivity, factory) to appCompatActivity.lifecycle
//        } else {
//            ViewModelProviders.of(fragment!!, factory) to fragment.lifecycle
//        }
//        val viewModel = viewModelProvider.get(BaseViewModel::class.java) as BaseViewModel<V, P>
//
//        var isPresenterCreated = false
//        if (viewModel.presenter == null) {
//            viewModel.presenter = createPresenter()
//            isPresenterCreated = true
//        }
//        presenter = viewModel.presenter!!
//        presenter.run {
//            attachLifecycle(lifecycle)
//            attachView((appCompatActivity ?: fragment) as V)
//            if (isPresenterCreated)
//                onPresenterCreate()
//        }
//    }
//}