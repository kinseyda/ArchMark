package com.kinsey.archmark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.kinsey.archmark.Graphics.TargetView
import com.kinsey.archmark.Model.TargetFace
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var targetFace = TargetFace(listOf<Float>(1f), listOf<Float>(1f))
        var targetView = TargetView(this,  targetFace)


        setContentView(R.layout.activity_main)

        val constraintLayout: ConstraintLayout = findViewById(R.id.target_layout)
        constraintLayout.addView(targetView)

    }
}
