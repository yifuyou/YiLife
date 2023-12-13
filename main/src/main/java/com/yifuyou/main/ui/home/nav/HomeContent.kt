/*
 * Copyright (c) 2023.  edit by Yifuyou
 */

package com.yifuyou.main.ui.home.nav

import com.yifuyou.main.ui.home.gallery.AddNewFragment
import com.yifuyou.main.ui.home.home.HomeListFragment
import com.yifuyou.main.ui.home.slideshow.SListshowFragment
import com.yifuyou.main.util.CommandUtil


object HomeContent {

    val cObjects : MutableList<String> = ArrayList()

    val cData : MutableMap<String, MenuData> = HashMap()

    var count : Int = 0

    private val MENU_DATA: MutableList<MenuData> = java.util.ArrayList()

    // 启动页
    private const val startIndex = 0

    fun getStartIndex(): Int {
        return startIndex
    }

    fun getMenuData(): List<MenuData>? {
        val fragmentNames =
            arrayOf<String>(HomeListFragment::class.java.getName(), AddNewFragment::class.java.getName(), SListshowFragment::class.java.getName())
        val title =
            arrayOf("列表", "新增", "加密列表")
        val icons =
            intArrayOf(CommandUtil.getDrawableId("ic_menu_list"), CommandUtil.getDrawableId("ic_menu_add"), CommandUtil.getDrawableId("ic_menu_slist"))
        for (i in fragmentNames.indices) {
            MENU_DATA.add(MenuData(0x998 + i, 1, title[i], icons[i], fragmentNames[i]))
        }
        return MENU_DATA
    }
}