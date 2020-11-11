package com.lxc.base.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lxc.base.R
import com.lxc.base.app.BaseApplication
import com.lxc.base.ui.activity.BaseActivity
import com.lxc.base.ui.interfaces.IBaseViewBehavior
import com.lxc.base.utils.BarUtils
import com.lxc.base.utils.LogUtil
import com.lxc.base.viewModel.BaseViewModel
import org.jetbrains.anko.toast

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment(),
    IBaseViewBehavior {
    protected lateinit var mViewModel: V

    protected lateinit var mBinding: T

    protected lateinit var mContext: Context

    protected lateinit var mActivity: BaseActivity<*, *>

    protected var mRefreshFlag = false

    private var mRootView: View? = null

    protected abstract fun getLayout(): Int

    protected abstract fun getViewModel(): Class<V>

    protected abstract fun initEventAndData()

    protected abstract fun initListeners()

    protected fun <K : BaseViewModel> getSharedViewModel(clazz: Class<K>): K {
        return getAppViewModelProvider().get(clazz)
    }

    private var isInit = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mActivity = context as BaseActivity<*, *>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        mViewModel = ViewModelProvider(this).get(getViewModel())
        mViewModel.mContext = mContext
        mBinding.lifecycleOwner = this
        //  将ViewModel生命周期与Activity绑定
        lifecycle.addObserver(mViewModel)
        if (mRootView == null || mRefreshFlag) {
            mRootView = mBinding.root
        }
        setStatusBarHeight()
        return mRootView
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
        lifecycle.removeObserver(mViewModel)
    }

    /**
     * 设置状态栏高度
     */
    private fun setStatusBarHeight() {
        val statusBar = mRootView?.findViewById<View>(R.id.view_status_bar)
        if (statusBar != null && statusBar.visibility == View.VISIBLE) {
            val layoutParams = statusBar.layoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = BarUtils.getStatusBarHeight()
            statusBar.layoutParams = layoutParams
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            if (!isHidden) {
                setInit()
            }
        } else {
            setInit()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!isInit && !hidden) {
            setInit()
        }
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun initViewModelListeners() {
        //  注册基础事件监听
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

    private fun setInit() {
        isInit = true
        initViewModelListeners()
        initEventAndData()
        initListeners()
    }

    protected fun openActivity(pClass: Class<*>, bundle: Bundle? = null) {
        val intent = Intent(activity, pClass)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        mContext.startActivity(intent)
    }

    override fun showProgress() {
//        mActivity.showProgress()
    }

    override fun hideProgress() {
//        mActivity.hideProgress()
    }

    override fun showToast(msg: String) {
        mContext.toast(msg)
    }

    override fun showError(msg: String) {
        LogUtil.d(msg)
    }

    // 给当前BaseFragment用的
    private fun getAppViewModelProvider(): ViewModelProvider {
        return (mActivity.applicationContext as BaseApplication).getAppViewModelProvider(mActivity)
    }
}