package com.daou.lib

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import java.text.DecimalFormat

@BindingAdapter("badgeContent")
fun TextView.setBadgeCountForBottomTab(content: String?) {
    background = null
    if (content != null) {
        val count = content.toIntOrNull()
        count?.let {
            if (it != 0) {
                setBadgeCount(it.toString())
                setBackgroundResource(R.drawable.menu_badge_bg)
            } else {
                text = null
            }
        } ?: kotlin.run {
            if (content.isNotBlank()) {
                text = content.trim()
                setBackgroundResource(R.drawable.menu_badge_bg)
            }
        }
    } else {
        text = ""
    }
}

@BindingAdapter("iconDrawableRes")
fun ImageView.setIconDrawable(iconDrawableRes: Int?) {
    iconDrawableRes?.let {
        setImageResource(it)
    }
}

@BindingAdapter("isClicked")
fun ImageView.setIsClicked(isClicked: Boolean?) {
    isClicked?.let {
        isSelected = it
    }
}

@BindingAdapter("badgeCountString")
fun TextView.setBadgeCount(count: String?) {
    count?.let { c ->
        c.toIntOrNull()?.let {
            text = if (it == 0) {
                null
            } else {
                val df = DecimalFormat("#,##0")
                if (it > 9999) "+9,999" else df.format(count.toLong())
            }
        } ?: kotlin.run {
            text = if (count == "N") "N" else ""
        }
    }
}

@BindingAdapter("pageAdapter", "canSwipe")
fun ViewPager2.setPageAdapter(fragmentStateAdapter: FragmentStateAdapter, canSwipe: Boolean) {
    adapter = fragmentStateAdapter
    isUserInputEnabled = canSwipe
}

@BindingAdapter("maxItemCountInSmall", "maxItemCountInLarge")
fun ScrollBottomNavigation.setMaxVisibleItemCount(
    maxItemCountInSmall: Int,
    maxItemCountInLarge: Int
) {
    if (context.deviceWidth >= 2000) {
        setMaxVisibleItemCount(maxItemCountInLarge)
    } else {
        setMaxVisibleItemCount(maxItemCountInSmall)
    }
}