package com.daou.lib

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Context.deviceWidth: Int
    get() {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager.currentWindowMetrics.bounds.width()
        } else {
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            size.x
        }
    }

fun View.show() {
    if (!isVisible) visibility = View.VISIBLE
}

fun View.gone() {
    if (isVisible) visibility = View.GONE
}

fun View.invisible() {
    if (isVisible) visibility = View.INVISIBLE
}