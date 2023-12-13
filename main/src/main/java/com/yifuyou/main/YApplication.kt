/*
 * Copyright (c) 2023.  edit by Yifuyou
 */

package com.yifuyou.main

import android.app.Activity
import android.app.Application
import android.os.Bundle


/**
 * 应用
 *
 * @author  zjf
 * @since 2023/12/2
 */
class YApplication : Application() {

    val activityTaskList : ArrayList<Activity> = ArrayList()

    private open class LifecycleCallback : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            doAction("onActivityCreated", activity, savedInstanceState)
        }

        override fun onActivityStarted(activity: Activity) {
            doAction("onActivityStarted", activity)
        }

        override fun onActivityResumed(activity: Activity) {
            doAction("onActivityResumed", activity)
        }

        override fun onActivityPaused(activity: Activity) {
            doAction("onActivityPaused", activity)
        }

        override fun onActivityStopped(activity: Activity) {
            doAction("onActivityStopped", activity)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            doAction("onActivitySaveInstanceState", activity, outState)
        }

        override fun onActivityDestroyed(activity: Activity) {
            doAction("onActivityDestroyed", activity)
        }

        open fun doAction(actionName: String, activity: Activity, bundle: Bundle? = null) {
            println(" lifecycle ${activity.localClassName} -- $actionName ")
        }
    }

    override fun onCreate() {
        super.onCreate()
        ModuleInitObj.modulesInit(this)
        registerActivityLifecycleCallbacks(LifecycleCallback())
    }
}