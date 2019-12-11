package com.kinsey.archmark.Graphics

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.kinsey.archmark.Model.TargetFace
import java.lang.Integer.min

class TargetView (context: Context, attrs: AttributeSet, private var targetFace: TargetFace): View(context, attrs) {
    private var ringDrawer: RingDrawer = RingDrawer()

    private var centerX = this.width/2
    private var centerY = this.height/2
    //TODO update these on view resize

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val diameter = min(this.width, this.height)
        for (ring in this.targetFace.rings) {
            this.ringDrawer.drawRing(canvas, ring, centerX, centerY, diameter)
        }
    }


}