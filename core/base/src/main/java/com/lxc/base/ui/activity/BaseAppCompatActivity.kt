package com.lxc.base.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.lxc.base.app.BaseApplication
import com.lxc.base.data.annotation.StatusBarMode
import com.lxc.base.ui.interfaces.IBaseViewBehavior
import com.lxc.base.ui.widget.LoadingDialog
import com.lxc.base.utils.BarUtils
import com.lxc.base.viewModel.BaseViewModel
import me.yokeyword.fragmentation.SupportActivity

abstract class BaseAppCompatActivity<T : ViewDataBinding, V : BaseViewModel> : SupportActivity(),
    IBaseViewBehavior {
    protected var mContext: FragmentActivity? = null

    protected lateinit var mViewModel: V

    protected lateinit var mBinding: T

    //  是否显示黑色状态栏
    private var showDarkStatus = true

    private var mLoadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        mBinding = DataBindingUtil.setContentView(mContext!!, getLayout())
        mViewModel = ViewModelProvider(mContext!!).get(getViewModel())
        mViewModel.mContext = this
        mBinding.lifecycleOwner = mContext
        //  将ViewModel生命周期与Activity绑定
        lifecycle.addObserver(mViewModel)

        initWindowFlags()
        reverseStatusColor()
        BaseApplication.instance().addActivity(this)
        initToolbar(savedInstanceState)

        initEventAndData()
        initListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
        BaseApplication.instance().finishActivity(this)
    }

    protected abstract fun getLayout(): Int

    protected abstract fun getViewModel(): Class<V>

    protected abstract fun initToolbar(savedInstanceState: Bundle?)

    protected abstract fun initEventAndData()

    protected abstract fun initListeners()

    protected fun <K : BaseViewModel> getSharedViewModel(clazz: Class<K>): K {
        return getAppViewModelProvider().get(clazz)
    }

    private fun getAppViewModelProvider(): ViewModelProvider {
        return (applicationContext as BaseApplication).getAppViewModelProvider(this)
    }

    protected open fun initWindowFlags() {}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            this.onBackPressedSupport()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    protected open fun reverseStatusColor() {
        if (showDarkStatus) {
            val mode: Int = BarUtils.setStatusBarLightMode(this, true)
            if (mode == StatusBarMode.OTHERWISE) {
                BarUtils.setStatusBarColor(this, Color.rgb(191, 191, 191))
            }
        }
    }

    protected fun showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog(this)
        }
        if (!mLoadingDialog!!.isShowing) {
            mLoadingDialog?.show()
        }
    }

    protected fun dismissLoading() {
        if (mLoadingDialog != null) {
            if (mLoadingDialog!!.isShowing) {
                mLoadingDialog!!.dismiss()
            }
            mLoadingDialog = null
        }
    }
}