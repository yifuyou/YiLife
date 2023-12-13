/*
 * Copyright (c) 2023.  edit by Yifuyou
 */

package com.yifuyou.main.ui.home

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.yifuyou.main.R
import com.yifuyou.main.databinding.ActivityHomeBinding
import com.yifuyou.main.ui.home.nav.MenuUtil
import kotlin.system.exitProcess

/**
 * 首页
 *
 * @author  zjf
 * @since 2023/12/2
 */
class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding

    val fragmentLifecycleListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
            super.onFragmentPreAttached(fm, f, context)
            doAction("onFragmentPreAttached", f)
        }

        override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
            super.onFragmentAttached(fm, f, context)
            doAction("onFragmentAttached", f)
        }

        override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
            super.onFragmentPreCreated(fm, f, savedInstanceState)
            doAction("onFragmentPreCreated", f)
        }

        override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
            super.onFragmentCreated(fm, f, savedInstanceState)
            doAction("onFragmentCreated", f)
        }

        override fun onFragmentActivityCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
            super.onFragmentActivityCreated(fm, f, savedInstanceState)
            doAction("onFragmentActivityCreated", f)
        }

        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            doAction("onFragmentViewCreated", f)
        }

        override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
            super.onFragmentStarted(fm, f)
            doAction("onFragmentStarted", f)
        }

        override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
            super.onFragmentResumed(fm, f)
            doAction("onFragmentResumed", f)
        }

        override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
            super.onFragmentPaused(fm, f)
            doAction("onFragmentPaused", f)
        }

        override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
            super.onFragmentStopped(fm, f)
            doAction("onFragmentStopped", f)
        }

        override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
            super.onFragmentSaveInstanceState(fm, f, outState)
            doAction("onFragmentSaveInstanceState", f)
        }

        override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
            super.onFragmentViewDestroyed(fm, f)
            doAction("onFragmentViewDestroyed", f)
        }

        override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
            super.onFragmentDestroyed(fm, f)
            doAction("onFragmentDestroyed", f)
        }

        override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
            super.onFragmentDetached(fm, f)
            doAction("onFragmentDetached", f)
        }

        fun doAction(actionName: String, fragment: Fragment) {
            println(" fragment lifecycle : ${fragment} $actionName")
        }
    }

    // 记录第一次返回触发时间
    private var lastBackPressTime: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleListener, true)

        binding = ActivityHomeBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        initAppBarNav()
    }

    // 初始化侧滑导航列表功能
    private fun initAppBarNav() {
        val toolbar = binding.appBarHome.toolbar
        setSupportActionBar(toolbar)

        binding.appBarHome.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_content_home)
        val navigator = FragmentNavigator(this, supportFragmentManager, R.id.nav_host_fragment_content_home)
        MenuUtil.setNavMenu(navController, binding.navView, navigator, R.navigation.mobile_navigation, null)

        appBarConfiguration =
            AppBarConfiguration(MenuUtil.getIds().toSet(), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // 两次返回退出
    override fun onBackPressed() {
        println(System.currentTimeMillis())
        if (lastBackPressTime != null && System.currentTimeMillis() - lastBackPressTime!! < 800L) {
            exitProcess(0)
        }

        Toast.makeText(this, "再次返回退出", Toast.LENGTH_SHORT).show()
        lastBackPressTime = System.currentTimeMillis()
    }
}