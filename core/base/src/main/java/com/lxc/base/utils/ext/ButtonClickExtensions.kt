package com.lxc.base.utils.ext

import android.view.MenuItem
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lxc.base.utils.ext.ViewClickDelay.DEFAULT_TIME

/**
 * 按钮防抖点击事件监听
 */
object ViewClickDelay {
    var hashCode: Int = 0
    var lastClickTime: Long = 0
    const val DEFAULT_TIME = 500L
}

fun View.clickDelay(delayTime: Long = DEFAULT_TIME, clickAction: () -> Unit) {
    this.setOnClickListener {
        if (this.hashCode() != ViewClickDelay.hashCode) {
            ViewClickDelay.hashCode = this.hashCode()
            ViewClickDelay.lastClickTime = System.currentTimeMillis()
            clickAction()
        } else {
            if (System.currentTimeMillis() - ViewClickDelay.lastClickTime > delayTime) {
                ViewClickDelay.lastClickTime = System.currentTimeMillis()
                clickAction()
            }
        }
    }
}

fun MenuItem.clickDelay(delayTime: Long = DEFAULT_TIME, clickAction: () -> Unit) {
    this.setOnMenuItemClickListener {
        if (this.hashCode() != ViewClickDelay.hashCode) {
            ViewClickDelay.hashCode = this.hashCode()
            ViewClickDelay.lastClickTime = System.currentTimeMillis()
            clickAction()
        } else {
            if (System.currentTimeMillis() - ViewClickDelay.lastClickTime > delayTime) {
                ViewClickDelay.lastClickTime = System.currentTimeMillis()
                clickAction()
            }
        }
        true
    }
}

fun <T, K : BaseViewHolder> BaseQuickAdapter<T, K>.clickDelay(
    delayTime: Long = DEFAULT_TIME,
    clickAction: (adapter: BaseQuickAdapter<T, K>, view: View, position: Int) -> Unit
) {
    this.setOnItemClickListener { adapter, view, position ->
        run {
            if (this.hashCode() != ViewClickDelay.hashCode) {
                ViewClickDelay.hashCode = this.hashCode()
                ViewClickDelay.lastClickTime = System.currentTimeMillis()
                clickAction(adapter as BaseQuickAdapter<T, K>, view, position)
            } else {
                if (System.currentTimeMillis() - ViewClickDelay.lastClickTime > delayTime) {
                    ViewClickDelay.lastClickTime = System.currentTimeMillis()
                    clickAction(adapter as BaseQuickAdapter<T, K>, view, position)
                }
            }
            true
        }
    }
}

fun <T, K : BaseViewHolder> BaseQuickAdapter<T, K>.childClickDelay(
    delayTime: Long = DEFAULT_TIME,
    clickAction: (adapter: BaseQuickAdapter<T, K>, view: View, position: Int) -> Unit
) {
    this.setOnItemChildClickListener { adapter, view, position ->
        run {
            if (this.hashCode() != ViewClickDelay.hashCode) {
                ViewClickDelay.hashCode = this.hashCode()
                ViewClickDelay.lastClickTime = System.currentTimeMillis()
                clickAction(adapter as BaseQuickAdapter<T, K>, view, position)
            } else {
                if (System.currentTimeMillis() - ViewClickDelay.lastClickTime > delayTime) {
                    ViewClickDelay.lastClickTime = System.currentTimeMillis()
                    clickAction(adapter as BaseQuickAdapter<T, K>, view, position)
                }
            }
            true
        }
    }
}