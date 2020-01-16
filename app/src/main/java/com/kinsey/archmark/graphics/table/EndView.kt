package com.kinsey.archmark.graphics.table

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.kinsey.archmark.MainActivity
import com.kinsey.archmark.model.Arrow
import com.kinsey.archmark.model.End

class EndView(context: Context, attrs: AttributeSet?): LinearLayout(context, attrs) {
    lateinit var end: End
    lateinit var mainActivity: MainActivity

    fun update(){
        this.removeAllViews()

        val size = Integer.max(this.mainActivity.getCard().defaultEndSize, this.mainActivity.getCard().getMostArrows()
        )

        for (i in 0 until size) {
            val arrowLst = end.arrows
            val tv = TextView(this.mainActivity!!)
            tv.layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
            tv.text = arrowLst.getOrNull(i)?.findScore()?.toInt()?.toString() ?: ""
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0f)
            tv.gravity = Gravity.CENTER
            this.addView(tv)
        }
    }

}