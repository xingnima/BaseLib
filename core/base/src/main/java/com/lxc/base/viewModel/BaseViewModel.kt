package com.lxc.base.viewModel

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lxc.base.data.BaseLiveData
import com.lxc.base.ui.interfaces.IBaseViewBehavior
import com.lxc.base.ui.interfaces.IViewModelLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), IBaseViewBehavior, IViewModelLifecycle {

    private lateinit var mLifecycleOwner: LifecycleOwner

    var mContext: Context? = null

    protected fun <T> launch(
        context: CoroutineContext = Dispatchers.Default,
        request: suspend CoroutineScope.() -> T?,
        onSuccess: (t: T) -> Unit,
        onError: () -> Unit = {}
    ) = viewModelScope.launch(context) {
        try {
            val t = request()
            hideProgress()
            if (t != null) {
                onSuccess(t)
            }
        } catch (e: Exception) {
            hideProgress()
            showToast(e.message ?: "")
            showError(e.message ?: "")
            onError()
        }
    }

    protected suspend fun <T> async(
        context: CoroutineContext = Dispatchers.Default,
        request: suspend CoroutineScope.() -> T?,
        onError: () -> Unit = {}
    ) = viewModelScope.async(context) {
        try {
            val t = request()
            hideProgress()
            t
        } catch (e: Exception) {
            hideProgress()
            showToast(e.message ?: "")
            showError(e.message ?: "")
            onError()
            null
        }
    }

    var loadingEvent = BaseLiveData<Boolean>()
        private set
    var toastEvent = BaseLiveData<String>()
        private set
    var errorEvent = BaseLiveData<String>()
        private set

    override fun showProgress() {
        loadingEvent.postValue(true)
    }

    override fun hideProgress() {
        loadingEvent.postValue(false)
    }

    override fun showToast(msg: String) {
        toastEvent.postValue(msg)
    }

    override fun showError(msg: String) {
        errorEvent.postValue(msg)
    }

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
        mLifecycleOwner = owner
    }

    override fun onCreate() {

    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {

    }
}