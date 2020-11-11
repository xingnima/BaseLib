package com.lxc.base.data.net

import com.lxc.base.constant.Constants
import com.lxc.base.data.db.entity.BaseCacheDataEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

open class BaseRepository<T : Any, K : Any>(clazz: Class<T>, clazzDao: Class<K>) {
    protected val mRetrofitService by inject(clazz)
    protected val mDao by inject(clazzDao)

    /**
     * 从数据库或远程获取数据
     *
     * @param dataBaseCall 从数据库获取数据的方法块
     * @param remoteCall 从远程获取数据的方法块
     * @param saveAction 远程获取数据后存储数据库的方法块
     *
     * @return 数据
     */
    suspend fun <T : Any, K : BaseCacheDataEntity<T>> apiDataBaseCall(
        dataBaseCall: suspend () -> K?,
        remoteCall: suspend () -> T,
        saveAction: (T) -> Unit,
        expireTime: Long = Constants.TimeArgs.TIME_CACHE_EXPIRE,
        refreshFlag: Boolean = false
    ) = withContext(IO) {
        if (refreshFlag) {
            val data = remoteCall()
            saveAction(data)
            data
        } else {
            val entity = dataBaseCall()
            //  若数据为空或已经过期，则重新请求数据
            if (entity == null ||
                (System.currentTimeMillis() - entity.saveTime) > expireTime
            ) {
                val data = remoteCall()
                saveAction(data)
                data
            } else {
                entity.data
            }
        }
    }

    class LoginFailedException(msg: String = "Login failed") : Exception(msg)
}