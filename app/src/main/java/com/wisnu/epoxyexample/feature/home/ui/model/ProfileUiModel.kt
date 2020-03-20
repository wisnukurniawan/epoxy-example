package com.wisnu.epoxyexample.feature.home.ui.model

data class ProfileUiModel(
    override val id: String,
    val name: String,
    val avatarUrl: String,
    val location: String,
    val bio: String,
    val blog: String
) : HomeUiItemModel