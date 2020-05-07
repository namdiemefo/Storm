package com.naemo.storm.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : DaggerAppCompatActivity() {

    private var mViewDataBinding: T? = null
    private var mViewModel: V? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.mViewModel = if (mViewModel == null) getViewModel() else mViewModel
        mViewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding?.executePendingBindings()
    }

    fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }


    fun getViewDataBinding(): T? {
        return mViewDataBinding
    }

    abstract fun getBindingVariable(): Int

    abstract fun getViewModel(): V?

    @LayoutRes
    abstract fun getLayoutId(): Int

    fun hideKeyBoard() {
        val view: View? = this.currentFocus
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager //casting
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }


    fun hideToolBar() {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}