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

    val topPadding = 80
    val bottomPadding = 80
    val leftPadding = 80
    val rightPadding = 80
    var paddedHeight = this.height - topPadding - bottomPadding
    var paddedWidth = this.width - leftPadding - rightPadding

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_UP) {
            val cm = ConversionUtils.pixelToCm(event.x, event.y, this, this.mainActivity.getCard().targetFace)
            val polar = ConversionUtils.cmCoordinatesToPolar(cm.first, cm.second, this.mainActivity.getCard().targetFace)
            val arrow = Arrow(polar.first, polar.second, this.mainActivity.getCard())
            this.mainActivity.getCard().addArrow(arrow)
        }
        
        return true
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        this.paddedHeight = this.height - topPadding - bottomPadding
        this.paddedWidth = this.width - leftPadding - rightPadding

        //First draw rings
        var centerX = this.leftPadding + (this.paddedWidth/2)
        var centerY = this.topPadding + (this.paddedHeight/2)
        val radius = min(this.paddedWidth, this.paddedHeight)/2
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