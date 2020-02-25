package com.kinsey.archmark.graphics.load

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kinsey.archmark.R
import com.kinsey.archmark.io.Head
import java.io.File

class CardChooserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_file_chooser)
        setSupportActionBar(findViewById(R.id.load_toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val fragment = supportFragmentManager.findFragmentById(R.id.card_chooser)

        (fragment as CardChooser).populate(File(this.filesDir.toString()))
    }

    private fun onSelect() {
        val head = Head(0, 0f, 0)
        intent.putExtra("time", head.time)
        intent.putExtra("total", head.total)
        intent.putExtra("arrows", head.arrows)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}