package com.wisnu.epoxyexample.feature.home.ui.model

sealed class HomeUiState {

  object ShowLoading : HomeUiState()

  data class Result(val list: List<HomeUiItemModel>) : HomeUiState()

  data class NextResult(val list: List<HomeUiItemModel>) : HomeUiState()

  data class Error(val error: Throwable) : HomeUiState()

}