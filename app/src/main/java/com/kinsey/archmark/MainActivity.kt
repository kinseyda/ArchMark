package com.kinsey.archmark

import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.kinsey.archmark.graphics.TargetView
import com.kinsey.archmark.model.Card
import com.kinsey.archmark.model.TargetFace
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.kinsey.archmark.graphics.TableFragment
import com.kinsey.archmark.graphics.TargetFragment


class MainActivity : AppCompatActivity() {
    //Model
    var targetFace:TargetFace = TargetFace(listOf<Float>(20f, 18f, 16f, 14f, 12f, 10f, 8f, 6f, 4f, 2f, 1f), listOf<Float>(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 11f), 40f)
    var card: Card = Card()

    //Fragments
    var targetFragment = TargetFragment(this.targetFace, this.card)
    var tableFragment = TableFragment(this.card)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager: ViewPager = findViewById(R.id.viewPager)
        viewPager.adapter = MainPagerAdapter(supportFragmentManager, targetFragment, tableFragment)


        targetFragment.parentContext = this
        tableFragment.parentContext = this

    }


    fun onFinishEndClicked(v: View) {
        tableFragment.updateEnd(this.targetFragment.targetView)
    }
}


private class MainPagerAdapter(fm: FragmentManager, private val targetFragment: TargetFragment, private val tableFragment: TableFragment) : FragmentStatePagerAdapter(fm) {

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
