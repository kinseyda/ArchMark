package com.kinsey.archmark.graphics

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.kinsey.archmark.R
import com.kinsey.archmark.model.Card
import com.kinsey.archmark.model.End
import java.util.Collections.max

class TableFragment(private val card: Card): Fragment() {

    private lateinit var arrowTable: TableLayout
    lateinit var parentContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val parentView = inflater.inflate(R.layout.arrow_table_layout, container, false)
        initView(parentView)
        return parentView
    }

    private fun initView(parentView: View) {
        this.arrowTable = parentView.findViewById(R.id.arrow_table)
        addArrowTableMargin(3)
    }

    private fun addArrowTableMargin(arrows: Int) {
        val row = TableRow(this.activity!!)
        row.layoutParams = TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT)


        row.addView(TextView(this.activity!!).apply { text = getString(R.string.end) })

        for (i in 1..arrows) {
            row.addView(TextView(this.activity!!).apply{text = getString(R.string.arrowNum, i)})
        }

        row.addView(TextView(this.activity!!).apply{text = getString(R.string.endTotal)})

        row.addView(TextView(this.activity!!).apply{text = getString(R.string.cumulativeTotal)})

        this.arrowTable.addView(row)
    }

    private fun addEnd(end: End, index: Int) {
        val row = TableRow(this.activity!!)

        row.addView(TextView(this.activity!!).apply { text = getString(R.string.endNum, index+1) })

        for (arrow in end.arrows) {
            row.addView(TextView(this.activity!!).apply{
                text = (arrow.findScore()).toString()
            })
        }

        //Add end total
        row.addView(TextView(this.activity!!).apply {
            text = end.endTotal().toString()
        })
        //Add cumulative total
        row.addView((TextView(this.activity!!).apply {
            text = card.cumulativeScore(index).toString()
        }))

        row.layoutParams = TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT)

        this.arrowTable.addView(row)
    }

    fun updateEnd(targetView: TargetView) {
        this.arrowTable.removeAllViews()

        addArrowTableMargin(this.card.getMostArrows())

        for (i in 0 until this.card.ends.size) {
            addEnd(this.card.ends[i], i)
        }

        this.card.newEnd()
        targetView.invalidate()
    }
}