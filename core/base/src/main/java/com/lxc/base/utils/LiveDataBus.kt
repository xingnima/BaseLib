package com.lxc.base.utils

import com.lxc.base.data.BaseLiveData

class LiveDataBus private constructor() {
    companion object {
        private var instance: LiveDataBus? = null

        fun getInstance(): LiveDataBus {
            if (instance == null) {
                synchronized(LiveDataBus::class.java) {
                    if (instance == null) {
                        instance = LiveDataBus()
                    }
                }
            }
            return instance!!
        }
    }

    private val bus by lazy {
        mutableMapOf<String, BaseLiveData<Any>>()
    }

    fun <T> getChannel(target: String, type: Class<T>): BaseLiveData<T> {
        if (!bus.containsKey(target)) {
            bus[target] = BaseLiveData()
        }
        return bus[target] as BaseLiveData<T>
    }

    fun getChannel(target: String): BaseLiveData<Any> {
        return getChannel(target, Any::class.java)
    }
}