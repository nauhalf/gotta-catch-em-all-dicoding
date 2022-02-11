package com.nauhalf.gottacatchemall.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _selectedMenu = MutableLiveData(0)
    val selectedMenu: LiveData<Int>
        get() = _selectedMenu

    fun setSelectedMenu(menu: Int) {
        _selectedMenu.value = menu
    }
}