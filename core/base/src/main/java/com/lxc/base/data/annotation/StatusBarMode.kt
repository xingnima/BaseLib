package com.lxc.base.data.annotation

import androidx.annotation.IntDef

/**
 * 状态栏类型
 * Created by lcy on 2019/1/7.
 * email:lcyzxin@gmail.com
 * version 1.0
 */
@IntDef(
    StatusBarMode.KITKAT,
    StatusBarMode.M,
    StatusBarMode.MIUI,
    StatusBarMode.FLYME,
    StatusBarMode.OTHERWISE
)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class StatusBarMode {
    companion object {
        const val KITKAT = 0 //  低于Android 21版本
        const val M = 1 //  大于等于Android 23版本
        const val MIUI = 2 //  小米
        const val FLYME = 3 //  魅族
        const val OTHERWISE = 4 //  其他
    }
}