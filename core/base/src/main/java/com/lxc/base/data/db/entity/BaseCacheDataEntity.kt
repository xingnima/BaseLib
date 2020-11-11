package com.lxc.base.data.db.entity

abstract class BaseCacheDataEntity<T> {
    abstract val id: Int
    abstract val saveTime: Long
    abstract val data: T
}