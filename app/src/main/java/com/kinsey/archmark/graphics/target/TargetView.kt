package com.kinsey.archmark.graphics.target

import android.content.Context
import android.graphics.Canvas
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.kinsey.archmark.MainActivity
import com.kinsey.archmark.graphics.marker.ArrowMarker
import com.kinsey.archmark.model.Arrow
import java.lang.Integer.min

class TargetView (context: Context, var mainActivity: MainActivity): View(context) {
    private var arrowMarkers: MutableList<ArrowMarker> = mutableListOf<ArrowMarker>()

    private var selectedMarker: ArrowMarker? = null

    val padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.getDisplayMetrics()).toInt()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val cm = ConversionUtils.pixelToCm(
            event.x,
            event.y,
            this,
            this.mainActivity.getCard().targetFace
        )
        val polar = ConversionUtils.cmCoordinatesToPolar(
            cm.first,
            cm.second,
            this.mainActivity.getCard().targetFace
        )
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
//                if (this.mainActivity.getCard().currentEnd.selected != null) {
                    val arrow = Arrow(polar.first, polar.second, this.mainActivity.getCard())
                    this.mainActivity.getCard().addArrow(arrow)
//                }
            }
            MotionEvent.ACTION_MOVE -> {
                this.mainActivity.getCard().moveCurrentArrow(polar.first, polar.second)
            }
            MotionEvent.ACTION_UP -> {
                this.mainActivity.getCard().currentEnd.selected = null
                this.mainActivity.getCard().change(null)
            }
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
            RingDrawer.drawRing(
                canvas,
                ring,
                this.mainActivity.getCard().targetFace,
                centerX,
                centerY,
                radius
            )
        }

        //Draw X
        XDrawer.drawX(canvas, centerX, centerY)

        this.arrowMarkers.clear()

        for (arrow in this.mainActivity.getCard().currentEnd.arrows) {
            val xy = ConversionUtils.arrowXY(arrow, this, this.mainActivity.getCard().targetFace)
            this.arrowMarkers.add(ArrowMarker(xy.first, xy.second, this))
        }

        //Draw arrow markers
        for (marker in this.arrowMarkers) {
            marker.drawMarker(canvas)
        }
    }


}