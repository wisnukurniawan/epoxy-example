package com.wisnu.epoxyexample.feature.home.domain.model

data class HomeModel(
    val isOffline: Boolean,
    val loadProfileState: LoadProfileState,
    val loadProjectState: LoadProjectState,
    val loadKotlinProjectState: LoadKotlinProjectState,
    val loadJavaProjectState: LoadJavaProjectState
) {

    companion object {
        fun initial(): HomeModel {
            return HomeModel(
                isOffline = false,
                loadProfileState = LoadProfileState.WaitingForData,
                loadProjectState = LoadProjectState.WaitingForData,
                loadKotlinProjectState = LoadKotlinProjectState.WaitingForData,
                loadJavaProjectState = LoadJavaProjectState.WaitingForData
            )
        }
    }

}

sealed class LoadProfileState {
    object WaitingForData : LoadProfileState()
    data class Loaded(val myProfileModel: ProfileUiModel) : LoadProfileState()
    data class Failed(val error: Throwable) : LoadProfileState()
}

sealed class LoadProjectState {
    object WaitingForData : LoadProjectState()
    data class Loaded(val myProjectModels: List<ProjectUiModel>) : LoadProjectState()
    data class Failed(val error: Throwable) : LoadProjectState()
}

sealed class LoadKotlinProjectState {
    object WaitingForData : LoadKotlinProjectState()
    data class Loaded(val kotlinProjectModels: List<ProjectUiModel>) : LoadKotlinProjectState()
    data class Failed(val error: Throwable) : LoadKotlinProjectState()
}

sealed class LoadJavaProjectState {
    object WaitingForData : LoadJavaProjectState()
    data class Loaded(val javaProjectModels: List<ProjectUiModel>) : LoadJavaProjectState()
    data class Failed(val error: Throwable) : LoadJavaProjectState()
}