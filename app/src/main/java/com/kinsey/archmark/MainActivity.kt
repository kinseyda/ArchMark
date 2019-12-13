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
    //Model
    var targetFace:TargetFace = TargetFace(listOf<Float>(20f, 18f, 16f, 14f, 12f, 10f, 8f, 6f, 4f, 2f, 1f), listOf<Float>(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 11f), 40f)
    var card: Card = Card()

    //Components
    var targetView: TargetView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.targetView = TargetView(this, this.targetFace, this.card)

        setContentView(R.layout.activity_main)

        val constraintLayout: ConstraintLayout = findViewById(R.id.target_layout)
        constraintLayout.addView(targetView)

    }
}
