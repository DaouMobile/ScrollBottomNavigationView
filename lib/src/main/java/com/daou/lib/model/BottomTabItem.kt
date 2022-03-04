package com.daou.lib.model

import androidx.annotation.DrawableRes

data class BottomTabItem(
    val appName: String,
    val text: String,
    val badgeContent: String?,
    val type: MenuCategory,
    @DrawableRes val iconRes: Int,
    val isClicked: Boolean = false,
    val isDormant: Boolean = false
)