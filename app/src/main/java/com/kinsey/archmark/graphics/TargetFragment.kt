package com.kinsey.archmark.graphics

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.kinsey.archmark.R
import com.kinsey.archmark.model.Card
import com.kinsey.archmark.model.TargetFace
import java.util.*

class TargetFragment(private val targetFace: TargetFace, private val card: Card): Fragment(), Observer {
    lateinit var targetView: TargetView

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

    private fun initView(parentView: View) {
        this.targetView = TargetView(this.parentContext, this.targetFace, this.card)
        val constraintLayout: ConstraintLayout = parentView.findViewById(R.id.target_holder)
        constraintLayout.addView(targetView)
    }

    override fun update(o: Observable?, arg: Any?) {
        this.targetView.invalidate()
    }

}