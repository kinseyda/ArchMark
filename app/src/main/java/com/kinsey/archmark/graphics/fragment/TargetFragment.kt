package com.kinsey.archmark.graphics.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.kinsey.archmark.MainActivity
import com.kinsey.archmark.R
import com.kinsey.archmark.graphics.table.EndView
import com.kinsey.archmark.graphics.target.TargetView
import java.util.*

class TargetFragment(private val mainActivity: MainActivity): Fragment(), Observer {
    lateinit var targetView: TargetView
    lateinit var endView: EndView

    lateinit var parentContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val parentView = inflater.inflate(R.layout.target_layout, container, false)
        initView(parentView)
        return parentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.endView = this.mainActivity.findViewById<EndView>(R.id.endView)
        this.endView.mainActivity = this.mainActivity
    }

    private fun initView(parentView: View) {
        this.targetView =
            TargetView(this.parentContext, this.mainActivity)
        val constraintLayout: ConstraintLayout = parentView.findViewById(R.id.target_holder)
        constraintLayout.addView(targetView)
    }

    override fun update(o: Observable?, arg: Any?) {
        this.targetView.invalidate()
        this.endView.end = this.mainActivity.getCard().currentEnd
        this.endView.update()
    }

}