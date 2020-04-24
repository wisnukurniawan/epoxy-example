package com.wisnu.epoxyexample.feature.home.domain.model

sealed class HomeEvent {

    object DataRequested : HomeEvent()

}