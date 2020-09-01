package com.wisnu.epoxyexample.presentation.home.model

sealed class HomeEffect {
    object RefreshData : HomeEffect()
}