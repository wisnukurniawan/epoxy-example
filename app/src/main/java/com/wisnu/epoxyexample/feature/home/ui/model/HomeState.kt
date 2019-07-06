package com.wisnu.epoxyexample.feature.home.ui.model

sealed class HomeState {

  object ShowLoading : HomeState()

  data class Result(val list: List<HomeItemModel>) : HomeState()

  data class Error(val error: Throwable) : HomeState()

}