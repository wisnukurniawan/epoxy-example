package com.wisnu.epoxyexample.presentation.home.model

import kotlinx.collections.immutable.PersistentList

data class HomeModel(
    val isOffline: Boolean,
    val loadDataState: LoadDataState
) {

    companion object {
        fun initial(): HomeModel {
            return HomeModel(
                isOffline = true,
                loadDataState = LoadDataState.WaitingForData
            )
        }
    }

}

sealed class LoadDataState {
    object WaitingForData : LoadDataState()
    object NoResult : LoadDataState()
    data class Loaded(
        val myProfileModel: ProfileUiModel,
        val myProjectModels: PersistentList<ProjectUiModel>,
        val kotlinProjectModels: PersistentList<ProjectUiModel>,
        val javaProjectModels: PersistentList<ProjectUiModel>
    ) : LoadDataState()
    data class Failed(val type: FeedbackType) : LoadDataState()
}

enum class FeedbackType {
    LOADING_ERROR
}