package com.kinsey.archmark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.kinsey.archmark.graphics.TargetView
import com.kinsey.archmark.model.Card
import com.kinsey.archmark.model.TargetFace


class MainActivity : AppCompatActivity() {
    //Model
    var targetFace:TargetFace = TargetFace(listOf<Float>(20f, 18f, 16f, 14f, 12f, 10f, 8f, 6f, 4f, 2f, 1f), listOf<Float>(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 11f), 40f)
    var card: Card = Card()

    //Components
    var targetView: TargetView? = null
    var arrowTable: TableLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.targetView = TargetView(this, this.targetFace, this.card)
        this.arrowTable = findViewById(R.id.arrowTable)
        addArrowTableMargin(3)

        val constraintLayout: ConstraintLayout = findViewById(R.id.target_layout)
        constraintLayout.addView(this.targetView)

    }


    private fun addArrowTableMargin(arrows: Int) {
        var row = TableRow(this)
        row.layoutParams = TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT)

        for (i in 1..arrows) {
            row.addView(TextView(this).apply{text = getString(R.string.arrowNum, i)})
        }

        row.addView(TextView(this).apply{text = getString(R.string.endTotal)})

        row.addView(TextView(this).apply{text = getString(R.string.cumulativeTotal)})

        this.arrowTable?.addView(row)
    }

    fun onFinishEndClicked(v: View) {
        var row = TableRow(this)

        for (arrow in card.currentEnd().arrows) {
            row.addView(TextView(this).apply{
                text = (arrow.findRing()?.score ?: 0f).toString()
            })
        }

        //Add end total
        row.addView(TextView(this).apply {
            text = card.currentEnd().endTotal().toString()
        })
        //Add cumulative total
        row.addView((TextView(this).apply {
            text = card.cumulativeScore().toString()
        }))

        row.layoutParams = TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT)

        this.arrowTable?.addView(row)

        this.card.newEnd()
        this.targetView?.invalidate()
    }


}
