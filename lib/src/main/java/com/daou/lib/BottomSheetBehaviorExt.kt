package com.daou.lib

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED

fun <T : View> BottomSheetBehavior<T>.show() {
    state = STATE_EXPANDED
}

fun <T : View> BottomSheetBehavior<T>.hide() {
    state = STATE_COLLAPSED
}