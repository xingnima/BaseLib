package com.lxc.base.binding

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lxc.base.R
import com.lxc.base.constant.Constants
import com.lxc.base.data.bean.BaseMultiItemBean
import com.lxc.base.data.result.BaseRecyclerViewResult

@BindingAdapter(value = ["mutableData"], requireAll = false)
fun <T> setMutableRecyclerView(
    recyclerView: RecyclerView,
    mutableData: MutableList<BaseMultiItemBean<T>>
) {
    val adapter =
        recyclerView.adapter as? BaseMultiItemQuickAdapter<BaseMultiItemBean<T>, BaseViewHolder>
            ?: return
    if (mutableData.isNullOrEmpty()) {
        adapter.setEmptyView(
            R.layout.base_core_layout_common_no_data,
            recyclerView.parent as ViewGroup
        )
    } else {
        adapter.setNewData(mutableData)
    }
    adapter.notifyDataSetChanged()
}

@BindingAdapter(value = ["data"], requireAll = false)
fun <T> setRecyclerView(recyclerView: RecyclerView, data: MutableList<T>) {
    val adapter =
        recyclerView.adapter as? BaseQuickAdapter<T, BaseViewHolder> ?: return
    if (data.isNullOrEmpty()) {
        adapter.data.clear()
        adapter.setEmptyView(
            R.layout.base_core_layout_common_no_data,
            recyclerView.parent as ViewGroup
        )
        recyclerView.adapter = adapter
    } else {
        adapter.setNewData(data)
        adapter.notifyDataSetChanged()
    }
}

@BindingAdapter(value = ["serverData"], requireAll = false)
fun <T> setServerDataRecyclerView(
    recyclerView: RecyclerView,
    serverData: BaseRecyclerViewResult<T>
) {
    val adapter =
        recyclerView.adapter as? BaseQuickAdapter<T, BaseViewHolder> ?: return
    when (serverData.dealFlag) {
        BaseRecyclerViewResult.DEAL_TYPE_ADD -> {
            if (serverData.data.size == 0) {
                adapter.loadMoreEnd()
            } else {
                adapter.addData(serverData.data)
                if (serverData.data.size < Constants.PAGE_SIZE) {
                    adapter.loadMoreEnd()
                } else {
                    adapter.loadMoreComplete()
                }
                adapter.notifyDataSetChanged()
            }
        }
        BaseRecyclerViewResult.DEAL_TYPE_REFRESH -> {
            if (serverData.data.size > 0) {
                recyclerView.scrollToPosition(0)
                adapter.setNewData(serverData.data)
                if (serverData.data.size < Constants.PAGE_SIZE) {
                    adapter.loadMoreEnd()
                }
            } else {
                adapter.data.clear()
                adapter.setEmptyView(
                    R.layout.base_core_layout_common_no_data,
                    recyclerView.parent as ViewGroup
                )
                recyclerView.adapter = adapter
            }
        }
        BaseRecyclerViewResult.DEAL_TYPE_MINUS -> {
            serverData.indexList.forEach {
                adapter.remove(it.key)
            }
            if (adapter.itemCount == 0) {
                adapter.setEmptyView(
                    R.layout.base_core_layout_common_no_data,
                    recyclerView.parent as ViewGroup
                )
            } else {
                adapter.notifyDataSetChanged()
            }
        }
        BaseRecyclerViewResult.DEAL_TYPE_UPDATE -> {
            serverData.indexList.forEach {
                adapter.data[it.key] = it.value
            }
            adapter.notifyDataSetChanged()
        }
    }
}