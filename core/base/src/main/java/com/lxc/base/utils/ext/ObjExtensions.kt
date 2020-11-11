package com.lxc.base.utils.ext

import java.lang.reflect.Field

fun Any.convertToMap(): MutableMap<String, String> {
    val map = mutableMapOf<String, String>()
    try {
        val clazz = this::class.java
        val field: Array<Field> = clazz.declaredFields
        for (f in field) {
            f.isAccessible = true
            if (f.get(this) == null) {
                map[f.name] = ""
            } else {
                map[f.name] = f.get(this)!!.toString()
            }
        }
    } catch (e: IllegalAccessException) {
        throw Exception(e.message, e)
    }
    return map
}