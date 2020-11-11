package com.lxc.base.ui.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.lxc.base.utils.LogUtil
import com.lxc.base.viewModel.BaseViewModel
import org.jetbrains.anko.toast

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : SimpleActivity<T, V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModelListeners()
    }

    /**
     * 注册基础事件监听
     */
    private fun initViewModelListeners() {
        mViewModel.loadingEvent.observe(this, Observer {
            if (it) {
                showProgress()
            } else {
                hideProgress()
            }
        })
        mViewModel.toastEvent.observe(this, Observer { showToast(it) })
        mViewModel.errorEvent.observe(this, Observer { showError(it) })
    }

    override fun showProgress() {
        showLoading()
    }

    override fun hideProgress() {
        dismissLoading()
    }

    override fun showToast(msg: String) {
        toast(msg)
    }

    override fun showError(msg: String) {
        LogUtil.d(msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
        lifecycle.removeObserver(mViewModel)
    }
}