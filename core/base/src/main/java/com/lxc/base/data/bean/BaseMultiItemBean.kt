package com.lxc.base.data.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

class BaseMultiItemBean<T>(var type: Int, var data: T) : MultiItemEntity {
    override fun getItemType(): Int {
        return type
    }
}