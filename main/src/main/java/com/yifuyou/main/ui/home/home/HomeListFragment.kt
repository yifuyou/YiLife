/*
 * Copyright (c) 2023.  edit by Yifuyou
 */

package com.yifuyou.main.ui.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.Fragment
import com.yifuyou.main.db.DataUtil
import com.yifuyou.main.pojo.PwdSaveItem
import com.yifuyou.main.util.CommandUtil

class HomeListFragment : Fragment() {
    private lateinit var dataList: ArrayList<PwdSaveItem>

    private var clickItemIndex = -1

    private lateinit var clickItem: PwdSaveItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataList = DataUtil.getAll()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                SetList()
            }
        }
    }

    @Composable
    fun SetList() {
        val mutableList = remember { mutableStateListOf<PwdSaveItem>() }
        mutableList.addAll(dataList)

        // 删除提醒窗口
        val isDeleteDialogOpen = remember { mutableStateOf(false) }
        ItemTryDelete(isDeleteDialogOpen) {
            DataUtil.delete(clickItem.itemId)
            mutableList.removeAt(clickItemIndex)
            dataList.removeAt(clickItemIndex)
        }

        // 查看操作窗口
        val isDialogOpen = remember { mutableStateOf(false) }
        ItemEventDialog(isDialogOpen, isDeleteDialogOpen) {
            DataUtil.update(clickItem)
            mutableList.apply {
                removeAt(clickItemIndex)
                add(clickItemIndex, clickItem)
            }
            dataList[clickItemIndex] = clickItem
        }
        LazyColumn(modifier = Modifier
            .fillMaxHeight()
            .background(Color(0x99aaaa11))) {
            item(0) {
                Text(text = "总数： ${mutableList.size}", fontSize = 22.sp)
            }
            items(mutableList.size) {
                val item = mutableList[it]
                Column(modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color(0xffaaaa66),
                        shape = RoundedCornerShape(10),
                    )
                    .clickable {
                        clickItemIndex = it
                        clickItem = item
                        isDialogOpen.value = true
                    }) {
                    Text(modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp), text = "账号：${item.userId} ", fontSize = 18.sp)
                    Text(modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp), text = "备注：${item.info}", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray))
            }
        }
    }

    @Composable
    fun ItemEventDialog(isDialogOpen: MutableState<Boolean>, isDeleteDialogOpen: MutableState<Boolean>, action: () -> Unit) {
        val isAnyChange = remember { mutableStateOf(false) }
        val isChangeAble = remember { mutableStateOf(false) }
        if (isDialogOpen.value) Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Column(modifier = Modifier
                .wrapContentSize(align = Alignment.Center)
                .background(Color.DarkGray, RoundedCornerShape(10))
                .padding(12.dp)) {
                SetFieldLine("账号", isAnyChange, isChangeAble) {
                    Button(modifier = Modifier.wrapContentSize(align = Alignment.CenterEnd), onClick = {
                        CommandUtil.copyData(clickItem.userId)
                        Toast.makeText(requireContext(), "账号复制成功", Toast.LENGTH_SHORT).show()
                    }) {
                        Text(text = "复制")
                    }
                }
                SetFieldLine("备注", isAnyChange, isChangeAble)
                SetFieldLine("密码", isAnyChange, isChangeAble) {
                    Button(onClick = {
                        CommandUtil.copyData(clickItem.pwd)
                        Toast.makeText(requireContext(), "密码复制成功", Toast.LENGTH_SHORT).show()
                    }) {
                        Text(text = "复制")
                    }
                }

                Spacer(modifier = Modifier.size(10.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    if (!isChangeAble.value) { // 修改按钮
                        Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow), onClick = {
                            isChangeAble.value = true
                        }) {
                            Text(text = "修改")
                        }
                    } else { // 保存按钮
                        Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green), onClick = {
                            isChangeAble.value = false
                            isDialogOpen.value = false
                            if (isAnyChange.value) {
                                action()
                            }
                        }) {
                            Text(text = "保存")
                        }
                    }
                    Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red), onClick = {
                        isChangeAble.value = false
                        isDialogOpen.value = false
                        isDeleteDialogOpen.value = true
                    }) {
                        Text(text = "删除")
                    }
                    Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray), onClick = {
                        isChangeAble.value = false
                        isDialogOpen.value = false
                    }) {
                        Text(text = "关闭")
                    }
                }
            }

        }
    }

    @Composable
    fun ItemTryDelete(isDeleteDialogOpen: MutableState<Boolean>, action: () -> Unit) {
        if (isDeleteDialogOpen.value) AlertDialog(onDismissRequest = {
            isDeleteDialogOpen.value = false
        }, title = { Text(text = "记录 ${dataList[clickItemIndex].userId}") }, text = { Text(text = "备注信息为 ${dataList[clickItemIndex].info} 是否确认删除？") }, confirmButton = {
            Button(onClick = { isDeleteDialogOpen.value = false }) {
                Text(text = "否")
            }
        }, dismissButton = {
            Button(onClick = {
                action(); isDeleteDialogOpen.value = false
            }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)) {
                Text(text = "是")
            }
        })
    }

    @Composable
    fun SetInfoText(str: String) {
        Text(text = " $str", fontSize = 16.sp, modifier = Modifier
            .padding(16.dp)
            .background(
                color = Color(10, 10, 10, 10),
                shape = RoundedCornerShape(20),
            ))
    }

    @Composable
    fun SetFieldText(str: String, isAnyChange: MutableState<Boolean>, isChangeAble: MutableState<Boolean>) {
        var input by remember { mutableStateOf("") }
        input = clickItem.getValueByTag(str)
        TextField(value = input, enabled = isChangeAble.value, textStyle = TextStyle(fontSize = 16.sp), onValueChange = {
            clickItem.setValueByTag(str, it)
            input = it
            isAnyChange.value = true
        }, placeholder = {
            Text(text = " textField $str")
        }, shape = RectangleShape, modifier = Modifier
            .fillMaxWidth(0.6f)
            .padding(end = 16.dp), singleLine = true, colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent, textColor = Color.DarkGray, disabledTextColor = Color.Green))
    }

    @Composable
    fun SetFieldLine(str: String, isAnyChange: MutableState<Boolean>, isChangeAble: MutableState<Boolean>, content: @Composable (() -> Unit)? = null) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(modifier = Modifier
                .padding(10.dp)
                .wrapContentHeight()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(40),
                ), verticalAlignment = Alignment.CenterVertically) {
                SetInfoText(str)
                SetFieldText(str, isAnyChange, isChangeAble)
            }
            content?.invoke()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}