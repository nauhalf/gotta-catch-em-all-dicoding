package com.nauhalf.gottacatchemall.ui.main

import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.nauhalf.gottacatchemall.R
import com.nauhalf.gottacatchemall.core.base.BaseActivity
import com.nauhalf.gottacatchemall.core.utils.FragmentPagerAdapter
import com.nauhalf.gottacatchemall.databinding.ActivityMainBinding
import com.nauhalf.gottacatchemall.ui.favorite.FavoriteFragment
import com.nauhalf.gottacatchemall.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.ArrayList

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
    }

    private fun setupPager() {
        val fragments: MutableList<Fragment> = ArrayList()
        fragments.add(HomeFragment())
        fragments.add(FavoriteFragment())
        binding.apply {
            viewPagerMain.adapter =
                FragmentPagerAdapter(fragments, supportFragmentManager, lifecycle)
            viewPagerMain.isUserInputEnabled = false

            bottomNavigation.setOnItemSelectedListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.navigation_home -> {
                        viewModel.setSelectedMenu(0)
                    }
                    R.id.navigation_favorite -> {
                        viewModel.setSelectedMenu(1)
                    }
                }
                true
            }
        }
    }

    private fun setUp() {
        setupPager()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.selectedMenu.observe(this) { menu ->
            if (menu != null) {
                binding.viewPagerMain.setCurrentItem(menu, false)
            }
        }
    }
}