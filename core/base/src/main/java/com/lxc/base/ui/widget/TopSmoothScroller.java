package com.lxc.base.ui.widget;

import android.content.Context;

import androidx.recyclerview.widget.LinearSmoothScroller;

/**
 * Created by lcy on 2019/1/12.
 * email:lcyzxin@gmail.com
 * version 1.0
 */
public class TopSmoothScroller extends LinearSmoothScroller {


    public TopSmoothScroller(Context context) {
        super(context);
    }

    @Override
    protected int getHorizontalSnapPreference() {
        return SNAP_TO_START;
    }

    @Override
    protected int getVerticalSnapPreference() {
        return SNAP_TO_START;
    }

}
