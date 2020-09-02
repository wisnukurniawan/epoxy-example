package com.wisnu.epoxyexample.feature.home.presentation.model

data class ProfileUiModel(
    val id: String,
    val name: String,
    val avatarUrl: String,
    val location: String,
    val bio: String,
    val blog: String
) {
    companion object {
        fun empty(): ProfileUiModel {
            return ProfileUiModel(
                "",
                "",
                "",
                "",
                "",
                ""
            )
        }
    }
}