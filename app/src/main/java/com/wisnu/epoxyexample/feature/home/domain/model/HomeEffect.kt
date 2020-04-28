package com.wisnu.epoxyexample.feature.home.domain.model

sealed class HomeEffect {
    object RefreshData : HomeEffect()
}