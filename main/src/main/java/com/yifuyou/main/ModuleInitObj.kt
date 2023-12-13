/*
 * Copyright (c) 2023.  edit by Yifuyou
 */

package com.yifuyou.main

import android.app.Application
import com.yifuyou.basemodule.YfyModuleInit


/**
 * 模块统一初始化操作
 *
 * @author  zjf
 * @since 2023/12/2
 */
object ModuleInitObj {
    fun modulesInit(application: Application) {
        arrayOf(
            "com.yifuyou.main.MainModuleInit"
        ).forEach {
            val toString = it
            val forName = Class.forName(toString)
            val newInstance = forName.newInstance()
            if (newInstance !is YfyModuleInit) {
                throw CloneNotSupportedException("class $toString is no supported to init")
            }

            newInstance.initModule(application)
        }
    }



}