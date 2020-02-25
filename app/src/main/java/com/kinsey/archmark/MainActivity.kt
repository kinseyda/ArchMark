package com.kinsey.archmark

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
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
        if (arg != this.card::moveCurrentArrow && arg != this.card::addArrow) {
            redoList.clear()
            undoList.add(0, this.old)
            this.undoList = this.undoList.subList(0, min(stackSize, this.undoList.size))
            this.old = this.card.copy()
        }
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

class Pager(context: Context, attributeSet: AttributeSet) : ViewPager(context, attributeSet) {
    lateinit var mainActivity: MainActivity

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (!this.mainActivity.movementInProgress) {
            super.onTouchEvent(event)
        } else false
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return if (!this.mainActivity.movementInProgress) {
            super.onInterceptTouchEvent(event)
        } else false
    }

}

class MainActivity : AppCompatActivity() {

    //Model
    var cardHistory = CardHistory()
    fun getCard() = this.cardHistory.card

    //Fragments
    private var targetFragment = TargetFragment(this)
    private var tableFragment = TableFragment(this)

    var movementInProgress = false //Used to disable pagination when moving around an arrow


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.LightTheme)
        setContentView(R.layout.activity_main)

        this.setSupportActionBar(findViewById(R.id.toolbar))

        val viewPager: Pager = findViewById(R.id.viewPager)
        viewPager.adapter = MainPagerAdapter(supportFragmentManager, targetFragment, tableFragment)
        viewPager.mainActivity = this

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
        val file = File(this.filesDir.toString() + "/Card" + ".amc")
        file.createNewFile()
        CardSaver.saveCard(this.getCard(), file)
    }

    fun onLoadClicked(menuItem: MenuItem) {
        try {
            this.cardHistory = CardHistory(CardLoader.loadCard(File(this.filesDir.toString() + "/Card" + ".amc")))
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
