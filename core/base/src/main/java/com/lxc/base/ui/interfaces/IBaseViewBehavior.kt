package com.lxc.base.ui.interfaces

interface IBaseViewBehavior {
    /**
     * 显示加载动画
     */
    fun showProgress()

    /**
     * 隐藏加载动画
     */
    fun hideProgress()

    /**
     * 提示
     *
     * @param msg 提示信息
     */
    fun showToast(msg: String)

    /**
     * 显示错误信息
     *
     * @param msg 错误信息
     */
    fun showError(msg: String)
}