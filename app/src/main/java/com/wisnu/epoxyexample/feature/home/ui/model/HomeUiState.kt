package com.wisnu.epoxyexample.feature.home.ui.model

sealed class HomeUiState {

  object ShowContent : HomeUiState()
  object ShowLoading : HomeUiState()
  object ShowLoadMore : HomeUiState()
  object HideLoadMore : HomeUiState()
  data class ShowError(val error: Throwable) : HomeUiState()
  data class ProfileResult(val profile: ProfileUiModel) : HomeUiState()
  data class TrendingProjectResult(val list: List<TrendingProjectUiModel>) : HomeUiState()
  data class ProjectResult(val list: List<ProjectUiModel>) : HomeUiState()
  data class NextProjectResult(val list: List<ProjectUiModel>) : HomeUiState()

}