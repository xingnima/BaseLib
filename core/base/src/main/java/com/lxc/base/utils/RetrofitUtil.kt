package com.lxc.base.utils

import com.lxc.base.data.net.cookie.CookieJarImpl
import com.lxc.base.data.net.cookie.PersistentCookieStore
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 创建RetrofitService对象
 */
object RetrofitUtil {

    val cookieStore = PersistentCookieStore()

    /**
     * 创建RetrofitService
     *
     * @param apiUrl 基地址
     * @param showLog 是否显示Log
     * @return T
     */
    inline fun <reified T> build(apiUrl: String, showLog: Boolean): T {
        val clientBuilder = OkHttpClient.Builder()
        if (showLog) {
            clientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        clientBuilder.cookieJar(CookieJarImpl(cookieStore))
        return Retrofit.Builder()
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(apiUrl)
            .build()
            .create(T::class.java)
    }

    /**
     * 创建带拦截器的RetrofitService
     *
     * @param apiUrl 基地址
     * @param showLog 是否显示Log
     * @param interceptors 拦截器
     * @return T
     */
    inline fun <reified T, V : Interceptor> buildWithInterceptor(
        apiUrl: String,
        showLog: Boolean,
        interceptors: MutableList<V>
    ): T {
        val clientBuilder = OkHttpClient.Builder()
        if (showLog) {
            clientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        interceptors.forEach {
            clientBuilder.addInterceptor(it)
        }

        return Retrofit.Builder()
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(apiUrl)
            .build()
            .create(T::class.java)
    }

    /**
     * 检查cookie是否可用
     */
    fun checkCookies() = cookieStore.checkCookies()
}