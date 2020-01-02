package com.kinsey.archmark.graphics

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.util.TypedValue
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.kinsey.archmark.MainActivity
import com.kinsey.archmark.R
import com.kinsey.archmark.model.Card
import com.kinsey.archmark.model.End
import java.lang.Integer.max
import java.util.*



class TableFragment(private val mainActivity: MainActivity): Fragment(), Observer {

    private lateinit var arrowTable: TableLayout
    lateinit var parentContext: Context

    private val defaultEndSize = 3

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
    }

    private fun addArrowTableMargin(arrows: Int) {
        val row = TableRow(this.activity!!)
        row.layoutParams = TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT)


        row.addView(TextView(this.activity!!).apply { text = getString(R.string.end); setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25.0f) })

        for (i in 1..arrows) {
            row.addView(TextView(this.activity!!).apply{text = getString(R.string.arrowNum, i); setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25.0f)})
        }

        row.addView(TextView(this.activity!!).apply{text = getString(R.string.endTotal); setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25.0f)})

        row.addView(TextView(this.activity!!).apply{text = getString(R.string.cumulativeTotal); setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25.0f)})

        this.arrowTable.addView(row)
    }

    private fun addEnd(end: End, index: Int) {
        val row = TableRow(this.activity!!)

        row.isClickable = true
        row.setOnClickListener { v ->
            // Current Row Index
            val cardIndex = arrowTable.indexOfChild(v)-1
            this.mainActivity.card.setCurrentEndTo(cardIndex)
        }

        row.addView(TextView(this.activity!!).apply { text = getString(R.string.endNum, index+1); setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0f) })

        val size = max(this.defaultEndSize, this.mainActivity.card.getMostArrows())

        for (i in 0 until size) {
            val arrowLst = end.arrows.sortedBy { it.distance }
            row.addView(TextView(this.activity!!).apply{
                text = arrowLst.getOrNull(i)?.findScore()?.toInt()?.toString() ?: ""
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0f)
            })
        }

        //Add end total
        row.addView(TextView(this.activity!!).apply {
            text = end.endTotal().toInt().toString()
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0f)
        })
        //Add cumulative total
        row.addView((TextView(this.activity!!).apply {
            text = mainActivity.card.cumulativeScore(index).toInt().toString()
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0f)
        }))

        row.layoutParams = TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT)

        this.arrowTable.addView(row)
    }


    override fun update(o: Observable?, arg: Any?) {
        updateEnd()
    }

    private fun updateEnd() {
        this.arrowTable.removeAllViews()

        val size = max(this.mainActivity.card.getMostArrows(), this.defaultEndSize)
        addArrowTableMargin(size)

        for (i in 0 until this.mainActivity.card.ends.size) {
            addEnd(this.mainActivity.card.ends[i], i)
        }

    }

    private class TablerowOnClickListener:View.OnClickListener {
        override fun onClick(v: View) {
            println("Type of v" + v.javaClass.name)
            v.setBackgroundColor(Color.BLACK)
        }
    };

}