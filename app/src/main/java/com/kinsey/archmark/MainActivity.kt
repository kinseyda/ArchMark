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
        var targetFace = TargetFace(listOf<Float>(20f, 18f, 16f, 14f, 12f, 10f, 8f, 6f, 4f, 2f, 1f), listOf<Float>(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 11f), 40f)
        var targetView = TargetView(this,  targetFace)


        setContentView(R.layout.activity_main)

        val constraintLayout: ConstraintLayout = findViewById(R.id.target_layout)
        constraintLayout.addView(targetView)

    }
}
