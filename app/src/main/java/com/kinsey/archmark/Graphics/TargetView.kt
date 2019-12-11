package com.kinsey.archmark.Graphics

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.kinsey.archmark.Model.TargetFace
import java.lang.Integer.min

class TargetView (context: Context, private var targetFace: TargetFace): View(context) {
    private var ringDrawer: RingDrawer = RingDrawer()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var centerX = this.width/2
        var centerY = this.height/2
        val radius = min(this.width, this.height)/2
        for (ring in this.targetFace.rings) {
            this.ringDrawer.drawRing(canvas, ring, centerX, centerY, radius)
        }
    }


}