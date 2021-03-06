package com.lxc.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import com.lxc.glide.listener.IGetBitmapListener;
import com.lxc.glide.listener.IGetDrawableListener;

import java.io.File;

/**
 * 定义图片加载客户端的一些接口方法
 *
 * @author lcy
 */

interface IImageLoaderClient {

    void init(Context context);

    void destroy(Context context);

    File getCacheDir(Context context);

    void clearMemoryCache(Context context);

    void clearDiskCache(Context context);

    Bitmap getBitmapFromCache(Context context, String url);

    void getBitmap(Context context, String url, IGetBitmapListener listener);

    void getBitmap(Context context, Uri uri, IGetBitmapListener listener);

    void getBitmap(Context context, @IdRes int resId, IGetBitmapListener listener);

    void displayImage(Context ctx, ImageLoader img);

    void displayImage(Fragment ctx, ImageLoader img);

    void displayImage(Activity ctx, ImageLoader img);

    void displayImage(Context context, String url, ImageView imageView);

    void displayImage(Context context, Uri uri, ImageView imageView);

    void getDrawable(Context context, String url, IGetDrawableListener listener);

    void getDrawable(Context context, Uri uri, IGetDrawableListener listener);

    void displayBlurImage(Context context, String url, ImageView imageView, int blurRadius);

    void displayBlurImage(Context context, Uri uri, ImageView imageView, int blurRadius);

    void displayBlurImage(Context context, int resId, ImageView imageView, int blurRadius);

    void displayImageInResource(Activity activity, int resId, ImageView imageView);

    void displayImageInResource(Context context, int resId, ImageView imageView);

    void displayImageInResource(Fragment fragment, int resId, ImageView imageView);

    /**
     * 停止图片的加载，对某一个的Activity
     */
    void clear(Activity activity, ImageView imageView);

    /**
     * 停止图片的加载，context
     */
    void clear(Context context, ImageView imageView);

    /**
     * 停止图片的加载，fragment
     */
    void clear(Fragment fragment, ImageView imageView);

    void displayImageErrorReload(Fragment fragment, String url, String fallbackUrl, ImageView imageView);

    void displayImageErrorReload(Activity activity, String url, String fallbackUrl, ImageView imageView);

    void displayImageErrorReload(Context context, String url, String fallbackUrl, ImageView imageView);


    //  失去焦点，建议实际的项目中少用，取消求情
    void glidePauseRequests(Context context);

    void glidePauseRequests(Activity activity);

    void glidePauseRequests(Fragment fragment);

    //  获取焦点，建议实际的项目中少用
    void glideResumeRequests(Context context);

    void glideResumeRequests(Activity activity);

    void glideResumeRequests(Fragment fragment);

}
