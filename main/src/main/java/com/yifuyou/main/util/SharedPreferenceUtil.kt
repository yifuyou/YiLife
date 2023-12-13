/*
 * Copyright (c) 2023.  edit by Yifuyou
 */
package com.yifuyou.main.util

import android.content.Context
import android.content.SharedPreferences
import com.yifuyou.main.util.SharedPreferenceUtil

class SharedPreferenceUtil {
    private var msp: SharedPreferences? = null
    fun getOrCreateSp(context: Context, name: String?, mode: Int): SharedPreferences? {
        if (msp != null) {
            msp = context.getSharedPreferences(name, mode)
        }
        return msp
    }

    fun saveString(key: String?, value: String?) {
        if (msp != null) {
            msp!!.edit().putString(key, value).apply()
        }
    }

    fun release() {
        msp = null
    }
}