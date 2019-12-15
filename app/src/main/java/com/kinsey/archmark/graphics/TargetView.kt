package com.kinsey.archmark.graphics

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import com.kinsey.archmark.model.Arrow
import com.kinsey.archmark.model.Card
import com.kinsey.archmark.model.TargetFace
import java.lang.Integer.min

class TargetView (context: Context, var targetFace: TargetFace, var card: Card): View(context) {
    private var ringDrawer: RingDrawer = RingDrawer()
    private var arrowMarkers: MutableList<ArrowMarker> = mutableListOf<ArrowMarker>()

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_UP) {
            val cm = pixelToCm(event.x, event.y, this, targetFace)
            val polar = cmCoordinatesToPolar(cm.first, cm.second, targetFace)
            val arrow = Arrow(polar.first, polar.second, targetFace)
            this.card.addArrow(arrow)
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

        this.arrowMarkers.clear()
        for (arrow in this.card.currentEnd().arrows) {
            this.arrowMarkers.add(ArrowMarker(arrow, this))
        }

        //Draw arrow markers
        for (marker in this.arrowMarkers) {
            marker.drawMarker(canvas)
        }
    }


}