package com.nauhalf.gottacatchemall.ui.main

import android.os.Bundle
import android.view.MenuItem
import com.nauhalf.gottacatchemall.R
import com.nauhalf.gottacatchemall.core.base.BaseActivity
import com.nauhalf.gottacatchemall.core.ui.FragmentPagerAdapter
import com.nauhalf.gottacatchemall.databinding.ActivityMainBinding
import com.nauhalf.gottacatchemall.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
    }

    private fun setUp() {
        val pager = FragmentPagerAdapter(
            listOf(HomeFragment(), HomeFragment()),
            supportFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = pager
        binding.viewPager.isUserInputEnabled = false
        binding.navigation.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    binding.viewPager.currentItem = 0
                }
                R.id.navigation_favorite -> {
                    binding.viewPager.currentItem = 1
                }
            }
            true
        }
    }

}