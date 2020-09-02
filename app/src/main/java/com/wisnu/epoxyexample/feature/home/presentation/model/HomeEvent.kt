package com.wisnu.epoxyexample.feature.home.presentation.model

sealed class HomeEvent {
    object RefreshDataRequested : HomeEvent()
    data class RefreshDataLoaded(val loadDataState: LoadDataState) : HomeEvent()
}