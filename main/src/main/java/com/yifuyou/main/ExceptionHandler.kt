/*
 * Copyright (c) 2023.  edit by Yifuyou
 */

package com.yifuyou.main

class ExceptionHandler(private val mHandler: Thread.UncaughtExceptionHandler?) :
    Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread, e: Throwable) {
        println("=================================================")
        println(t.id.toString() + " :  " + e.toString())
        println("=================================================")
        mHandler?.uncaughtException(t, e)
    }
}