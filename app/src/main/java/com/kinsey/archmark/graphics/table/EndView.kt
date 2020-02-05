package com.kinsey.archmark.graphics.table

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.kinsey.archmark.MainActivity
import com.kinsey.archmark.R
import com.kinsey.archmark.model.Arrow
import com.kinsey.archmark.model.End
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.annotation.ColorInt
import android.content.res.Resources.Theme
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class EndView(context: Context, attrs: AttributeSet?): LinearLayout(context, attrs) {
    lateinit var end: End
    lateinit var mainActivity: MainActivity
    var selectColor: Int
    init {
        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(R.attr.colorButtonNormal, typedValue, true)
        selectColor = typedValue.data
    }


    fun update(){
        this.removeAllViews()

        val size = Integer.max(this.mainActivity.getCard().defaultEndSize, this.mainActivity.getCard().getMostArrows())

        val arrowLst = end.arrows
        
        for (i in 0 until size) {
            val tv = TextView(this.mainActivity!!)
            tv.layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
            tv.text = arrowLst.getOrNull(i)?.findScore()?.toInt()?.toString() ?: ""
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0f)
            tv.gravity = Gravity.CENTER
            tv.setOnClickListener { v ->
                val arrowIndex = this.indexOfChild(v)
                this.mainActivity.getCard().setCurrentArrowTo(arrowIndex)
            }
            if (i == this.end.selected) {
                tv.setBackgroundColor(selectColor)
            }
            this.addView(tv)
        }
    }

}