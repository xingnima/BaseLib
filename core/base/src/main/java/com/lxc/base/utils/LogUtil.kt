package com.lxc.base.utils

import android.util.Log
import com.lxc.base.BuildConfig

/**
 * Log工具类
 *
 *
 * 封装系统Log工具类，便于统一Log的输出和关闭
 *
 * @author lcy
 */
object LogUtil {
    private const val DEBUG = BuildConfig.LOG_ENABLE
    private const val APP_NAME = "JET_PACK"
    fun v(msg: String): Int {
        return v("", msg)
    }

    fun d(msg: String): Int {
        return d("", msg)
    }

    fun i(msg: String): Int {
        return i("", msg)
    }

    fun w(msg: String): Int {
        return w("", msg)
    }

    fun e(msg: String): Int {
        return e("", msg)
    }

    fun v(tag: String?, msg: String): Int {
        return if (DEBUG) Log.v(
            APP_NAME,
            getTracePrefix("v") + msg
        ) else 0
    }

    fun d(tag: String, msg: String): Int {
        return if (DEBUG) Log.d(
            APP_NAME,
            getTracePrefix("d") + tag + " " + msg
        ) else 0
    }

    fun i(tag: String, msg: String): Int {
        return if (DEBUG) Log.i(
            APP_NAME,
            getTracePrefix("i") + tag + " " + msg
        ) else 0
    }

    fun w(tag: String, msg: String): Int {
        return if (DEBUG) Log.w(
            APP_NAME,
            getTracePrefix("w") + tag + " " + msg
        ) else 0
    }

    fun e(tag: String, msg: String): Int {
        return if (DEBUG) Log.e(
            APP_NAME,
            getTracePrefix("e") + tag + " " + msg
        ) else 0
    }

    fun v(tag: String, msg: String, tr: Throwable?): Int {
        return if (DEBUG) Log.v(APP_NAME, "$tag: $msg", tr) else 0
    }

    fun d(tag: String, msg: String, tr: Throwable?): Int {
        return if (DEBUG) Log.d(APP_NAME, "$tag: $msg", tr) else 0
    }

    fun i(tag: String, msg: String, tr: Throwable?): Int {
        return if (DEBUG) Log.i(APP_NAME, "$tag: $msg", tr) else 0
    }

    fun w(tag: String, msg: String, tr: Throwable?): Int {
        return if (DEBUG) Log.w(APP_NAME, "$tag: $msg", tr) else 0
    }

    fun e(tag: String, msg: String, tr: Throwable?): Int {
        return if (DEBUG) Log.e(APP_NAME, "$tag: $msg", tr) else 0
    }

    private fun getTracePrefix(logLevel: String): String {
        val sts = Throwable().stackTrace
        var st: StackTraceElement? = null
        for (i in sts.indices) {
            if (sts[i].methodName.equals(logLevel, ignoreCase = true)
                && i + 2 < sts.size
            ) {
                if (sts[i + 1].methodName.equals(logLevel, ignoreCase = true)) {
                    st = sts[i + 2]
                    break
                } else {
                    st = sts[i + 1]
                    break
                }
            }
        }
        if (st == null) {
            return ""
        }
        var clsName = st.className
        clsName = if (clsName.contains("$")) {
            clsName.substring(
                clsName.lastIndexOf(".") + 1,
                clsName.indexOf("$")
            )
        } else {
            clsName.substring(clsName.lastIndexOf(".") + 1)
        }
        return clsName + "-> " + st.methodName + "():"
    }

    fun show(str: String) {
        var str = str
        str = str.trim { it <= ' ' }
        var index = 0
        val maxLength = 4000
        var sub = ""
        while (index < str.length) { // java的字符不允许指定超过总的长度end
            sub = if (str.length <= index + maxLength) {
                str.substring(index)
            } else {
                str.substring(index, index + maxLength)
            }
            index += maxLength
        }
        d(sub.trim { it <= ' ' })
    }
}