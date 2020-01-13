package com.kinsey.archmark.graphics

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import com.kinsey.archmark.MainActivity
import com.kinsey.archmark.model.Arrow
import com.kinsey.archmark.model.Card
import com.kinsey.archmark.model.TargetFace
import java.lang.Integer.min

class TargetView (context: Context, var mainActivity: MainActivity): View(context) {
    private var arrowMarkers: MutableList<ArrowMarker> = mutableListOf<ArrowMarker>()

    val padding = 300

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_UP) {
            val cm = ConversionUtils.pixelToCm(event.x, event.y, this, this.mainActivity.getCard().targetFace)
            val polar = ConversionUtils.cmCoordinatesToPolar(cm.first, cm.second, this.mainActivity.getCard().targetFace)
            val arrow = Arrow(polar.first, polar.second, this.mainActivity.getCard())
            println("Distance: " + arrow.distance)
            println("Score: " + arrow.findScore())
            this.mainActivity.getCard().addArrow(arrow)
        }
        
        return true
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //First draw rings
        var centerX = this.width/2
        var centerY = this.height/2
        val radius = min(this.width-(padding*2), this.height-(padding*2))/2
        for (ring in this.mainActivity.getCard().targetFace.rings) {
            RingDrawer.drawRing(canvas, ring, this.mainActivity.getCard().targetFace, centerX, centerY, radius)
        }

        this.arrowMarkers.clear()

        for (arrow in this.mainActivity.getCard().currentEnd.arrows) {
            this.arrowMarkers.add(ArrowMarker(arrow, this))
        }

        //Draw arrow markers
        for (marker in this.arrowMarkers) {
            marker.drawMarker(canvas)
        }
    }


}