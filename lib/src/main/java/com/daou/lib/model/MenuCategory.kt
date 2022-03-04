package com.daou.lib.model

sealed class MenuCategory(val typeString: String)

object HOME : MenuCategory(MENU_TYPE_HOME)

object NOTIFICATION : MenuCategory(MENU_TYPE_NOTIFICATION)

class WEB(val url: String) : MenuCategory(MENU_TYPE_WEB)

object ETC : MenuCategory(MENU_TYPE_ETC)

const val MENU_TYPE_HOME = "HOME"
const val MENU_TYPE_NOTIFICATION = "NOTIFICATION"
const val MENU_TYPE_WEB = "WEB"
const val MENU_TYPE_ETC = "ETC"