package com.kinsey.archmark

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.kinsey.archmark.graphics.TableFragment
import com.kinsey.archmark.graphics.TargetFragment
import com.kinsey.archmark.io.CardLoader
import com.kinsey.archmark.io.CardSaver
import com.kinsey.archmark.model.Card
import com.kinsey.archmark.model.TargetFace
import java.io.File
import java.io.IOException


class MainActivity : AppCompatActivity() {
    //Model
    var card: Card = Card()

    //Fragments
    private var targetFragment = TargetFragment( this)
    private var tableFragment = TableFragment(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager: ViewPager = findViewById(R.id.viewPager)
        viewPager.adapter = MainPagerAdapter(supportFragmentManager, targetFragment, tableFragment)

        initCard()

        targetFragment.parentContext = this
        tableFragment.parentContext = this

    }

    private fun initCard() {
        this.card.addObserver(this.targetFragment)
        this.card.addObserver(this.tableFragment)
    }


    fun onFinishEndClicked(v: View) {
        this.card.newEnd()
    }

    fun onUndoClicked(v: View) {
        this.card.removeLastArrow()

    }

    fun onClearClicked(v: View) {
        println("Clear!")
        card.clear()
    }


    fun onSaveClicked(v: View) {
        val file = File(this.filesDir.toString() + "/Card" + ".txt")
        println("Created:" + file.createNewFile())
        CardSaver.saveCard(this.card, file)
        println("Files in dir   : ")
        for (i in this.fileList()) {
            println(i)
        }
        println("Contents of Card.txt:")
        File(this.filesDir.toString() + "/Card.txt").forEachLine { println(it) }
    }

    fun onLoadClicked(v: View) {
        try {
            this.card = CardLoader.loadCard(File(this.filesDir.toString() + "/Card" + ".txt"))
            initCard()
            this.card.change()
        }
        catch(e: IOException) {
            println(e.message)
        }
    }

}


private class MainPagerAdapter(fm: FragmentManager, private val targetFragment: TargetFragment, private val tableFragment: TableFragment) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int  = 2


    override fun getItem(i: Int): Fragment {
        lateinit var fragment: Fragment
        if (i == 0) {
            fragment = targetFragment
        }
        else {
            fragment = tableFragment
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "OBJECT ${(position + 1)}"
    }
}
