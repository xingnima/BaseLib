package com.lxc.base.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import com.lxc.base.R;

/**
 * 打开三方链接
 *
 * @author lh
 */
public class OpenUrlUtil {

    /**
     * 用系统浏览器打开链接
     *
     * @param context context
     * @param url     链接
     */
    public static void openBrowser(Context context, String url) {
        try {
            openActivity(context, url, null, null);
        } catch (ActivityNotFoundException e) {
            ToastUtils.showToast(context.getString(R.string.base_core_xylink_not_installed));
            e.printStackTrace();
        }
    }

    private static void openActivity(Context context, String url, String packageName, String activityClassName) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        if (!TextUtils.isEmpty(packageName) && !TextUtils.isEmpty(activityClassName)) {
            intent.setClassName(packageName, activityClassName);
        }
        context.startActivity(intent);
    }

    /**
     * 调用拨号界面
     *
     * @param phone 电话号码
     */
    public static void call(Context context, String phone) {
        call(context, Uri.parse("tel:" + phone));
    }

    public static void call(Context context, Uri phoneUri) {
        Intent intent = new Intent(Intent.ACTION_DIAL, phoneUri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 检测该包名所对应的应用是否存在
     * * @param packageName
     */
    private static boolean checkPackage(Context context, String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 是否拦截请求地址
     *
     * @param context 上下文
     * @param url     请求链接
     * @return 返回true 则表示拦截,false则不处理,让后面逻辑处理
     */
    public static boolean shouldInterceptScheme(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (url.startsWith("jianshu")) {
            openBrowser(context, url);
        }
        //  处理常见的文档类型
//        if (url.endsWith(".pdf") || url.endsWith(".doc") || url.endsWith(".docx")
//                || url.endsWith(".xls") || url.endsWith(".xlsx") || url.endsWith(".ppt") || url.endsWith(".pptx")) {
//            openBrowser(context, url);
//            return true;
//        }
        //  处理电话
        if (url.startsWith("tel:")) {
            call(context, Uri.parse(url));
            return true;
        }
        return false;
    }
}
