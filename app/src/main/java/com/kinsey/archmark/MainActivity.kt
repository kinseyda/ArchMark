package com.kinsey.archmark

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.kinsey.archmark.graphics.fragment.TableFragment
import com.kinsey.archmark.graphics.fragment.TargetFragment
import com.kinsey.archmark.io.CardLoader
import com.kinsey.archmark.io.CardSaver
import com.kinsey.archmark.model.Card
import java.io.File
import java.io.IOException
import android.view.Menu
import android.view.MenuItem
import java.lang.Integer.min
import java.util.*


class CardHistory(var card: Card = Card()): Observable(), Observer {
    private var undoList = mutableListOf<Card>()
    private var redoList = mutableListOf<Card>()

    private var stackSize = 5

    private var old = this.card.copy()

    init {
        observeCard()
    }


    override fun update(o: Observable?, arg: Any?) {
        redoList.clear()
        undoList.add(0, this.old)
        this.undoList = this.undoList.subList(0, min(stackSize, this.undoList.size))
        this.old = this.card.copy()
        change()
    }

    private fun observeCard() {
        this.card.deleteObservers()
        this.card.addObserver(this)
    }

    fun change() {
        this.setChanged()
        this.notifyObservers()
    }

    fun undoEmpty() = undoList.isEmpty()
    fun redoEmpty() = redoList.isEmpty()

    fun undo() {
        if (!undoEmpty()) {
            redoList.add(0, this.card.copy())
            this.card = undoList.removeAt(0)
            this.old = this.card.copy()
            this.observeCard()
            this.change()
        }
    }

    fun redo() {
        if (!redoEmpty()) {
            undoList.add(0, this.card.copy())
            this.card = redoList.removeAt(0)
            this.old = this.card.copy()
            this.observeCard()
            this.change()
        }
    }

}

class MainActivity : AppCompatActivity() {

    //Model
    var cardHistory = CardHistory()
    fun getCard() = this.cardHistory.card

    //Fragments
    private var targetFragment = TargetFragment(this)
    private var tableFragment = TableFragment(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setSupportActionBar(findViewById(R.id.toolbar))

        val viewPager: ViewPager = findViewById(R.id.viewPager)
        viewPager.adapter = MainPagerAdapter(supportFragmentManager, targetFragment, tableFragment)

        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        tabLayout.setupWithViewPager(viewPager)

        setSupportActionBar(findViewById(R.id.toolbar))

        initHistory()

        targetFragment.parentContext = this
        tableFragment.parentContext = this

    }

    private fun initHistory() {
        this.cardHistory.addObserver(this.targetFragment)
        this.cardHistory.addObserver(this.tableFragment)
    }

    fun onFinishEndClicked(v: View) {
        this.getCard().newEnd()
    }

    fun onUndoClicked(menuItem: MenuItem) {
        this.cardHistory.undo()

    }

    fun onRedoClicked(menuItem: MenuItem) {
        this.cardHistory.redo()

    }

    fun onClearClicked(menuItem: MenuItem) {
        this.getCard().clear()
    }


    fun onSaveClicked(menuItem: MenuItem) {
        val file = File(this.filesDir.toString() + "/Card" + ".txt")
        file.createNewFile()
        CardSaver.saveCard(this.getCard(), file)
    }

    fun onLoadClicked(menuItem: MenuItem) {
        try {
            this.cardHistory = CardHistory(CardLoader.loadCard(File(this.filesDir.toString() + "/Card" + ".txt")))
            initHistory()
            this.cardHistory.change()
        }
        catch(e: IOException) {
            println(e.message)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity_toolbar, menu)
        return true
    }

}


private class MainPagerAdapter(fm: FragmentManager, private val targetFragment: TargetFragment, private val tableFragment: TableFragment) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

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
        return if (position == 0) "Target" else "Scorecard"
    }
}
