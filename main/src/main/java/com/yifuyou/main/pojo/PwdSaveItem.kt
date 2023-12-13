/*
 * Copyright (c) 2023.  edit by Yifuyou
 */

package com.yifuyou.main.pojo


/**
 * 账号密码保存类
 *
 * @author  zjf
 * @since 2023/12/11
 */
class PwdSaveItem(var itemId: Int, var userId: String, var info: String, var pwd: String) {
    var isSec: Boolean = false // 是否是特殊加密类型

    constructor(userId: String, info: String, pwd: String) : this(-1, userId, info, pwd)

    constructor() : this("", "", "")

    private val keyList = listOf("账号", "备注", "密码")

    fun getValueByTag(tag: String) : String {
        return when(tag) {
            keyList[0] -> userId
            keyList[1] -> info
            keyList[2] -> pwd
            else -> ""
        }
    }

    fun setValueByTag(tag: String, value: String) {
         when(tag) {
            keyList[0] -> userId = value
            keyList[1] -> info = value
            keyList[2] -> pwd = value
        }
    }

    fun clone() : PwdSaveItem {
        return PwdSaveItem(itemId, userId, info, pwd)
    }

    fun copy(src : PwdSaveItem = PwdSaveItem()) {
        itemId = src.itemId
        userId = src.userId
        info = src.info
        pwd = src.pwd
    }
}