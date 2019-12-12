package com.kinsey.archmark.Graphics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.kinsey.archmark.Model.Arrow
import com.kinsey.archmark.Model.Card
import com.kinsey.archmark.Model.TargetFace
import java.lang.Integer.min

class TargetView (context: Context, var targetFace: TargetFace): View(context) {
    private var ringDrawer: RingDrawer = RingDrawer()
    private var arrowMarkers: MutableList<ArrowMarker> = mutableListOf<ArrowMarker>()
    var card = Card()

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_UP) {
            val cm = pixelToCm(event.x, event.y, this, targetFace)
            val polar = cmCoordinatesToPolar(cm.first, cm.second, targetFace)
            val arrow = Arrow(polar.first, polar.second)
            this.card.addArrow(arrow)
            this.arrowMarkers.add(ArrowMarker(arrow, this))
        }

        //View is now "out of date" so we have to tell it to draw again
        invalidate()
        return true
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //First draw rings
        var centerX = this.width/2
        var centerY = this.height/2
        val radius = min(this.width, this.height)/2
        for (ring in this.targetFace.rings) {
            this.ringDrawer.drawRing(canvas, ring, this.targetFace, centerX, centerY, radius)
        }

        //Draw arrow markers
        for (marker in this.arrowMarkers) {
            marker.drawMarker(canvas)
        }
    }


}