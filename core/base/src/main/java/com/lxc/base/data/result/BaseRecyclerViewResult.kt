package com.lxc.base.data.result

open class BaseRecyclerViewResult<T>(
    val dealFlag: Int = DEAL_TYPE_REFRESH,
    val data: MutableList<T> = mutableListOf(),
    val indexList: MutableMap<Int, T> = mutableMapOf()
) {
    companion object {
        //  操作类型:刷新
        const val DEAL_TYPE_REFRESH = 0

        //  操作类型:增加
        const val DEAL_TYPE_ADD = 1

        //  操作类型:减少
        const val DEAL_TYPE_MINUS = 2

        //  操作类型:更新
        const val DEAL_TYPE_UPDATE = 3
    }
}