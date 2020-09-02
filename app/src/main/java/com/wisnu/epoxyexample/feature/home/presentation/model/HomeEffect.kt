package com.wisnu.epoxyexample.feature.home.presentation.model

sealed class HomeEffect {
    object RefreshData : HomeEffect()
}