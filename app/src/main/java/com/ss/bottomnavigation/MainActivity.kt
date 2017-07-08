package com.ss.bottomnavigation

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ss.bottomnavigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private var fragment1: Fragment? = null

    private var fragment2: Fragment? = null

    private var fragment3: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        setSupportActionBar(binding!!.toolbar)
        supportActionBar!! .title = SampleFragment.TAG_1
        disableShiftingMode(binding!!.bottomNavigation)

        binding!!.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            item.isChecked = true
            when (item.itemId) {
                R.id.nav_item_1 -> {
                    changeFragment(fragment1, SampleFragment.TAG_1)
                }
                R.id.nav_item_2 -> {
                    changeFragment(fragment2, SampleFragment.TAG_2)
                }
                R.id.nav_item_3 -> {
                    changeFragment(fragment3, SampleFragment.TAG_3)
                }
            }
            false
        }
        initFragments(savedInstanceState)
    }

    private fun initFragments(savedInstanceState: Bundle?) {
        val manager = supportFragmentManager
        fragment1 = manager.findFragmentByTag(SampleFragment.TAG_1)
        fragment2 = manager.findFragmentByTag(SampleFragment.TAG_2)
        fragment3 = manager.findFragmentByTag(SampleFragment.TAG_3)

        if (fragment1 == null) {
            fragment1 = SampleFragment.newInstance(SampleFragment.TAG_1)
        }
        if (fragment2 == null) {
            fragment2 = SampleFragment.newInstance(SampleFragment.TAG_2)
        }
        if (fragment3 == null) {
            fragment3 = SampleFragment.newInstance(SampleFragment.TAG_3)
        }

        if (savedInstanceState == null) {
            changeFragment(fragment1, SampleFragment.TAG_1)
        }
    }

    private fun disableShiftingMode(view: BottomNavigationView) {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false
            for (i in 0..menuView.childCount - 1) {
                val itemView = menuView.getChildAt(i) as BottomNavigationItemView
                itemView.setShiftingMode(false)
                itemView.setChecked(itemView.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {
            Log.e(e.toString(), "could not get shifting mode")
        } catch (e: IllegalAccessException) {
            Log.e(e.toString(), "could not change shifting mode value")
        }

    }

    private fun changeFragment(fragment: Fragment?, tag: String): Boolean {
        if (fragment!!.isAdded) {
            return false
        }
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()

        val currentFragment = manager.findFragmentById(R.id.content_view)
        if (currentFragment != null) {
            transaction.detach(currentFragment)
        }
        if (fragment.isDetached) {
            transaction.attach(fragment)
        } else {
            transaction.add(R.id.content_view, fragment, tag)
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()

        manager.executePendingTransactions()

        binding!!.toolbar.title = fragment.tag

        return true
    }

    override fun onBackPressed() {
        if (changeFragment(fragment1, SampleFragment.TAG_1)) {
            binding!!.toolbar.title = fragment1!!.tag
            binding!!.bottomNavigation.menu.findItem(R.id.nav_item_1).isChecked = true
            return
        }
        super.onBackPressed()
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName
    }
}
