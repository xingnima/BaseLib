package com.lxc.base.utils.ext

import com.afollestad.materialdialogs.MaterialDialog

fun MaterialDialog.release() {
    if (this.isShowing) {
        this.dismiss()
    }
}