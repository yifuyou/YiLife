/*
 * Copyright (c) 2023.  edit by Yifuyou
 */

package com.yifuyou.main.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gyf.immersionbar.ImmersionBar
import com.yifuyou.main.R
import com.yifuyou.main.ui.home.HomeActivity

/**
 * 闪屏界面
 *
 * @author  zjf
 * @since 2023/12/2
 */
class YSplashedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ImmersionBar.with(this)
            .statusBarColor(TOP_HEADER_COLOR_STRING)
            .fitsSystemWindows(true)
            .autoDarkModeEnable(true)
            .init()
        setContent {
            MiddleText(SHOW_TEXT)
        }

        val handler = Handler(Looper.myLooper()!!)

        // 延时跳转
        handler.postDelayed(
            {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }, AUTO_TURN_DELAY_MILLIS
        )
    }

    override fun onBackPressed() {
    }

    @Composable
    fun MiddleText(name: String) {
        val rotation = remember { Animatable(0f) }
        LaunchedEffect(true) {
            rotation.animateTo(-360f,
                //delayMillis = 动画开始延迟时间 ， durationMillis = 动画持续时间 ， easing = 动画数值曲线
                animationSpec = infiniteRepeatable(animation = tween(durationMillis = AUTO_TURN_DELAY_MILLIS.toInt(), easing = LinearEasing))
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(TOP_HEADER_COLOR)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                color = Color.White,
                fontSize = 30.sp,
                fontStyle = FontStyle.Italic,
                text = name
            )
            Image(
                painter = painterResource(id = R.drawable.ic_toys),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.DarkGray, shape = CircleShape)
                    .graphicsLayer {
                        rotationZ = rotation.value
                    }
            )
        }
    }

    @Preview
    @Composable
    fun Previewer(){
        MiddleText("this")
    }

    companion object {
        private const val SHOW_TEXT = "逸~生活"

        private const val TOP_HEADER_COLOR = 0x99999999

        private const val TOP_HEADER_COLOR_STRING = "#99999999"

        private const val AUTO_TURN_DELAY_MILLIS = 3000L
    }
}