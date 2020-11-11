package com.lxc.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.Display;
import android.view.WindowManager;

/**
 * 屏幕参数工具类.
 *
 * @author lcy
 */
public class DisplayUtil {

    //  屏幕宽度
    private static int displayWidthPixels = 0;
    private static int displayHeightPixels = 0;

    /**
     * 获取屏幕参数
     */
    private static void getDisplayMetrics(Context context) {
        // 宽度
        displayWidthPixels = context.getResources().getDisplayMetrics().widthPixels;
        // 高度
        displayHeightPixels = context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getDisplayWidthPixels(Context context) {
        if (context == null) {
            return -1;
        }
        if (displayWidthPixels == 0) {
            getDisplayMetrics(context);
        }
        return displayWidthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getDisplayheightPixels(Context context) {
        if (context == null) {
            return -1;
        }
        if (displayHeightPixels == 0) {
            getDisplayMetrics(context);
        }
        return displayHeightPixels;
    }

    /**
     * 将px值转换为dip或dp值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 屏幕方向
     *
     * @param activity 上下文
     * @return 0:横屏
     * 1:竖屏
     */
    public static int screenOrientation(Context activity) {
        int orient = ((Activity) activity).getRequestedOrientation();
        if (orient != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE && orient != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            WindowManager windowManager = ((Activity) activity).getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            int screenWidth = display.getWidth();
            int screenHeight = display.getHeight();
            orient = screenWidth < screenHeight ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        }
        return orient;
    }
}
