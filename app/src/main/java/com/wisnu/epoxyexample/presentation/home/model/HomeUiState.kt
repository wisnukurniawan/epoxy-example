package com.wisnu.epoxyexample.presentation.home.model

sealed class HomeUiState {

    object ShowContent : HomeUiState()
    object ShowLoading : HomeUiState()
    data class ShowError(val error: Throwable) : HomeUiState()
    data class ProfileResult(val model: ProfileUiModel) : HomeUiState()
    data class KotlinProjectResult(val model: List<ProjectUiModel>) : HomeUiState()
    data class JavaProjectResult(val model: List<ProjectUiModel>) : HomeUiState()
    data class ProjectResult(val model: List<ProjectUiModel>) : HomeUiState()

}