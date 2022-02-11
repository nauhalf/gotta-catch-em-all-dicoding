package com.nauhalf.gottacatchemall.ui.favorite

import androidx.fragment.app.viewModels
import com.nauhalf.gottacatchemall.R
import com.nauhalf.gottacatchemall.core.base.BaseFragment
import com.nauhalf.gottacatchemall.databinding.FragmentFavoriteBinding
import com.nauhalf.gottacatchemall.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {
    private val viewModel by viewModels<HomeViewModel>()
    override fun baseOnCreateView() {

    }

}