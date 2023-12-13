/*
 * Copyright (c) 2023.  edit by Yifuyou
 */
package com.yifuyou.main.ui.home.nav

import android.os.Bundle
import android.view.Menu
import androidx.annotation.NavigationRes
import androidx.navigation.NavController
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.fragment.FragmentNavigator
import com.google.android.material.navigation.NavigationView
import java.lang.ref.WeakReference

/**
 * navigation 工具类
 *
 * @author zjf
 * @since 2023/11/26
 */
object MenuUtil {
    private const val TAG = "MenuUtil"

    // 侧滑功能列表
    private val DATA = HomeContent.getMenuData()

    // 设置功能项
    fun setNavMenu(navController: NavController, navigationView: NavigationView, navigator: FragmentNavigator, @NavigationRes defaultNavMenu: Int, actions: MenuActions?) {
        val navigationViewMenu = navigationView.menu
        var graph: NavGraph? = null
        graph = navController.navInflater.inflate(defaultNavMenu)


        if (DATA!!.isNotEmpty()) {
            graph.clear()
            navigationViewMenu.clear()
        }

        for (i in DATA.indices) {
            val menuData = DATA[i]
            navigationViewMenu.add(menuData.groupId, menuData.id, Menu.NONE, menuData.title)
            if (menuData.icon != -1) navigationViewMenu.getItem(i).setIcon(menuData.icon)
            val destination = navigator.createDestination().setClassName(menuData.fragmentClassName)
            destination.id = menuData.id
            graph.addDestination(destination)
        }
        graph.startDestination = DATA[HomeContent.getStartIndex()].id
        navController.graph = graph
        if (actions == null) {
            return
        }
        val navigationViewWeakReference = WeakReference(navigationView)
        val finalNavController: NavController = navController
        navController.addOnDestinationChangedListener(object : OnDestinationChangedListener {
            override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
                val view = navigationViewWeakReference.get()
                if (view == null) {
                    finalNavController.removeOnDestinationChangedListener(this)
                    return
                }
                for (i in DATA.indices) {
                    if (DATA[i].id == view.id) {
                        actions.onPageChange(i)
                        break
                    }
                }
            }
        })
    }

    fun getIds(): List<Int> {
        val list = arrayListOf<Int>()
        DATA?.forEach { item -> list.add(item.id) }
        return list
    }
}