package com.lxc.base.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
import com.lxc.base.R
import com.lxc.base.ui.widget.TopSmoothScroller

object RecyclerViewHelper {
    /**
     * 配置垂直列表RecyclerView
     *
     * @param view
     */
    fun initRecyclerViewV(
        context: Context?,
        view: RecyclerView,
        adapter: RecyclerView.Adapter<*>?
    ) {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        initRecyclerView(layoutManager, view, adapter)
    }

    fun initRecyclerView(
        manager: LinearLayoutManager?,
        view: RecyclerView,
        adapter: RecyclerView.Adapter<*>?
    ) {
        view.layoutManager = manager
        view.itemAnimator = DefaultItemAnimator()
        view.adapter = adapter
    }

    fun initRecyclerViewV(
        context: Context?,
        view: RecyclerView,
        adapter: BaseQuickAdapter<*, *>,
        listener: RequestLoadMoreListener?
    ) {
        initRecyclerViewV(context, view, adapter)
        adapter.setOnLoadMoreListener(listener, view)
    }

    /**
     * 配置水平列表RecyclerView
     *
     * @param view
     */
    fun initRecyclerViewH(
        context: Context?,
        view: RecyclerView,
        adapter: RecyclerView.Adapter<*>?
    ) {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        view.layoutManager = layoutManager
        view.itemAnimator = DefaultItemAnimator()
        view.adapter = adapter
    }

    /**
     * 配置网格列表RecyclerView
     *
     * @param view
     */
    fun initRecyclerViewG(
        context: Context?,
        view: RecyclerView,
        adapter: RecyclerView.Adapter<*>?,
        column: Int
    ) {
        val layoutManager =
            GridLayoutManager(context, column, LinearLayoutManager.VERTICAL, false)
        view.layoutManager = layoutManager
        view.itemAnimator = DefaultItemAnimator()
        view.adapter = adapter
    }

    /**
     * 配置瀑布流列表RecyclerView
     *
     * @param view
     */
    fun initRecyclerViewSV(
        context: Context?,
        view: RecyclerView,
        adapter: RecyclerView.Adapter<*>?,
        column: Int
    ) {
        val layoutManager =
            StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL)
        view.layoutManager = layoutManager
        view.itemAnimator = DefaultItemAnimator()
        view.adapter = adapter
    }

    fun isFullScreen(
        recyclerView: RecyclerView?,
        adapter: BaseQuickAdapter<*, *>?,
        listener: OnFullScreenListener?
    ) {
        if (recyclerView == null || adapter == null || listener == null) return
        val manager = recyclerView.layoutManager ?: return
        if (manager is LinearLayoutManager) {
            recyclerView.postDelayed(Runnable {
                if (isFullScreen(
                        manager,
                        adapter.itemCount
                    )
                ) {
                    listener.fullScreen()
                }
            }, 50)
        } else if (manager is StaggeredGridLayoutManager) {
            val staggeredGridLayoutManager =
                manager
            recyclerView.postDelayed(Runnable {
                val positions =
                    IntArray(staggeredGridLayoutManager.spanCount)
                staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(positions)
                val pos =
                    getTheBiggestNumber(positions) + 1
                if (pos != adapter.itemCount) {
                    listener.fullScreen()
                }
            }, 50)
        }
    }

    private fun isFullScreen(llm: LinearLayoutManager, itemCount: Int): Boolean {
        return llm.findLastCompletelyVisibleItemPosition() + 1 != itemCount ||
                llm.findFirstCompletelyVisibleItemPosition() != 0
    }

    private fun getTheBiggestNumber(numbers: IntArray?): Int {
        var tmp = -1
        if (numbers == null || numbers.size == 0) {
            return tmp
        }
        for (num in numbers) {
            if (num > tmp) {
                tmp = num
            }
        }
        return tmp
    }

    @JvmOverloads
    fun moveToPosition(recyclerView: RecyclerView, position: Int, smooth: Boolean = false) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        val manager =
            recyclerView.layoutManager as LinearLayoutManager? ?: return
        val orientation = manager.orientation
        val firstItem = manager.findFirstVisibleItemPosition()
        val lastItem = manager.findLastVisibleItemPosition()
        //然后区分情况
        if (position <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            recyclerView.scrollToPosition(position)
        } else if (position <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                val left = recyclerView.getChildAt(position - firstItem).left
                if (smooth) {
                    recyclerView.smoothScrollBy(left, 0)
                } else {
                    recyclerView.scrollBy(left, 0)
                }
            } else {
                val top = recyclerView.getChildAt(position - firstItem).top
                if (smooth) {
                    recyclerView.smoothScrollBy(0, top)
                } else {
                    recyclerView.scrollBy(0, top)
                }
            }
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            if (smooth) {
                recyclerView.smoothScrollToPosition(position)
            } else {
                recyclerView.scrollToPosition(position)
            }
            //这里这个变量是用在RecyclerView滚动监听里面的
        }
    }

    fun moveToTopPosition(recyclerView: RecyclerView, position: Int) {
        val manager =
            recyclerView.layoutManager as LinearLayoutManager?
        if (manager != null) {
            val mScroller = TopSmoothScroller(recyclerView.context)
            mScroller.targetPosition = position
            manager.startSmoothScroll(mScroller)
        }
    }

    @SuppressLint("MissingPermission")
    fun showError(adapter: BaseQuickAdapter<*, *>, recyclerView: RecyclerView) {
        if (adapter.data.size == 0) {
            adapter.setEmptyView(
                if (NetworkUtils.isConnected()) {
                    R.layout.base_core_layout_common_service_error
                } else {
                    R.layout.base_core_layout_common_net_error
                },
                recyclerView.parent as ViewGroup
            )
            recyclerView.adapter = adapter
        } else {
            if (adapter.isLoading) {
                adapter.loadMoreFail()
            }
        }
    }
}

interface OnFullScreenListener {
    fun fullScreen()
}