/*
 * Copyright (c) 2023.  edit by Yifuyou
 */
package com.yifuyou.main.util

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import androidx.core.content.getSystemService
import java.lang.NullPointerException

/**
 * 通用工具类
 *
 * @author zjf
 * @since 2023/11/26
 */
object CommandUtil {
    private var mContext: Application? = null
    fun init(application: Application?) {
        mContext = application
    }

    fun getResId(context: Context?, name: String?, type: String?, packageName: String?): Int {
        if (context == null) {
            throw NullPointerException("Context is null")
        }
        return context.resources.getIdentifier(name, type, packageName)
    }

    fun getDrawableId(name: String?): Int {
        return if (mContext == null || name == null || "" == name) {
            -1
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mContext!!.resources.getIdentifier(name, "drawable", mContext!!.opPackageName)
        } else {
            mContext!!.resources.getIdentifier(name, "drawable", mContext!!.packageName)
        }
    }

    fun copyData(data:String) {
        if (mContext == null) {
            return
        }
        (mContext!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).apply {
            setPrimaryClip(ClipData.newPlainText( null, data))
        }

    }
}