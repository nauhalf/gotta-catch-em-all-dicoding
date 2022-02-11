package com.nauhalf.gottacatchemall.core.utils


sealed class UiAction {
    object Search : UiAction()
    object Scroll : UiAction()
}

data class UiState(
    val hasNotScrolledForCurrentSearch: Boolean = false,
)