package com.wisnu.epoxyexample.feature.home.domain.model

data class HomeModel(
    val isOffline: Boolean,
    val homeLoadDataState: HomeLoadDataState
)

sealed class HomeLoadDataState {
    object WaitingForData : HomeLoadDataState()
    data class Loaded(val uiModel: Data) : HomeLoadDataState()
    data class Failed(val error: Throwable) : HomeLoadDataState()
}

data class Data(
    val myProfileModel: ProfileUiModel,
    val myProjectModels: List<ProjectUiModel>,
    val kotlinProjectModels: List<ProjectUiModel>,
    val javaProjectModels: List<ProjectUiModel>
)