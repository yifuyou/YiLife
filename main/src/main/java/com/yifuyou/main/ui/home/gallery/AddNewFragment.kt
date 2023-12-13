/*
 * Copyright (c) 2023.  edit by Yifuyou
 */

package com.yifuyou.main.ui.home.gallery

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.yifuyou.main.db.DataUtil
import com.yifuyou.main.pojo.PwdSaveItem

class AddNewFragment : Fragment() {

    private val keyList = listOf("账号", "备注", "密码")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        return ComposeView(requireContext()).apply {
            setContent {
                SetPage()
            }
        }
    }

    @SuppressLint("MutableCollectionMutableState")
    @Composable
    fun SetPage() {
        var typeCheck by rememberSaveable { mutableStateOf(false) }
        val values by rememberSaveable { mutableStateOf(HashMap<String, MutableState<String>>()) }
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(10.dp)) {

            Spacer(modifier = Modifier.size(66.dp))
            keyList.forEach { SetFieldLine(it, values) }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = typeCheck, onCheckedChange = {
                    typeCheck = it
                })
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = "特级加密")
            }
            if (typeCheck) Text(text = "check !!")

            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(50),
                onClick = {
                    val pwdItem = PwdSaveItem()
                    pwdItem.userId = values["账号"]?.value ?:""
                    pwdItem.info = values["备注"]?.value ?:""
                    pwdItem.pwd = values["密码"]?.value ?:""
                    DataUtil.insert(pwdItem).apply {
                        Toast.makeText(requireContext(), "添加成功", Toast.LENGTH_SHORT).show()
                        values.let { map-> map.forEach { entry -> run { entry.value.value = "" } } }
                    }
                }) {
                Text(text = "保存")

            }
        }
    }

    @Composable
    fun SetInfoText(str: String) {
        Text(text = " $str", fontSize = 18.sp, modifier = Modifier
            .padding(16.dp)
            .background(
                color = Color(10, 10, 10, 10),
                shape = RoundedCornerShape(20),
            ))
    }

    @Composable
    fun SetFieldText(str: String, values: HashMap<String, MutableState<String>>) {
        var input by remember { mutableStateOf("").apply {
            values[str] = this
        } }

        TextField(value = input,
            textStyle = TextStyle(fontSize = 20.sp),
            onValueChange = { input = it }, placeholder = {
            Text(text = " textField $str")
        }, shape = RectangleShape,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent,
                textColor = Color.DarkGray))
    }

    @Composable
    fun SetFieldLine(str: String, values: HashMap<String, MutableState<String>>, content: @Composable (() -> Unit)? = null) {
        Row(modifier = Modifier
            .padding(10.dp)
            .wrapContentHeight()
            .background(
                color = Color(10, 10, 10, 10),
                shape = RoundedCornerShape(40),
            ), verticalAlignment = Alignment.CenterVertically) {
            SetInfoText(str)
            SetFieldText(str, values)
        }
        content?.invoke()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}