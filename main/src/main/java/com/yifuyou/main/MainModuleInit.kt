/*
 * Copyright (c) 2023.  edit by Yifuyou
 */

package com.yifuyou.main

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.yifuyou.basemodule.YfyModuleInit
import com.yifuyou.main.db.DataUtil
import com.yifuyou.main.util.CommandUtil


/**
 * 当前模块初始化
 *
 * @author  zjf
 * @since 2023/12/2
 */
class MainModuleInit : YfyModuleInit {
    override fun initModule(application: Application?) {
        if (application == null) {
            throw NullPointerException("context is null")
        }

        // 注册异常事件处理
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler(
            Thread.getDefaultUncaughtExceptionHandler()))
        if(BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(application)
        CommandUtil.init(application)
        DataUtil.init(application)
    }
}