package com.kinsey.archmark.graphics.load

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.kinsey.archmark.R
import com.kinsey.archmark.io.CardLoader
import com.kinsey.archmark.io.Head
import java.io.File
import java.io.IOException


class CardChooser : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.card_file_chooser, container, false)
    }

    fun populate(filesDir: File) {
        val lst = mutableListOf<Head>()
        val files = File(filesDir.toString()).listFiles()?.filter { it.name.endsWith(".amc") }
        for (f in files!!) {
            try {
                lst.add(CardLoader.loadHead(f))
            } catch (e: IOException) {
                println(e.message)
            }
        }

        val content: LinearLayout = this.activity?.findViewById(R.id.content_layout)!!

        val factory = LayoutInflater.from(this.activity)
        for (h in lst) {
            val cardFile: View = factory.inflate(R.layout.card_file_layout, content, false)
            content.addView(cardFile)
        }

    }

}