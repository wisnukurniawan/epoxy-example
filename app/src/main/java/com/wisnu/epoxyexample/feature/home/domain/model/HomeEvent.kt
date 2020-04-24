package com.wisnu.epoxyexample.feature.home.domain.model

sealed class HomeEvent {

    object ProfileRequested : HomeEvent()
    object ProjectRequested : HomeEvent()
    object ProjectKotlinRequested : HomeEvent()
    object ProjectJavaRequested : HomeEvent()

}