package com.wisnu.epoxyexample.presentation.home.model

sealed class HomeEvent {
    object RefreshDataRequested : HomeEvent()
    data class RefreshDataLoaded(val loadDataState: LoadDataState) : HomeEvent()
}