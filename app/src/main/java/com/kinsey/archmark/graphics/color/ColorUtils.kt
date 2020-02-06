package com.kinsey.archmark.graphics.color

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import com.kinsey.archmark.R

object ColorUtils {

    fun selectionBG(context: Context): Int {
        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(R.attr.colorButtonNormal, typedValue, true)
        return typedValue.data
    }

}