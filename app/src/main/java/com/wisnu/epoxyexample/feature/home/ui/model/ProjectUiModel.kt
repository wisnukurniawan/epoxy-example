package com.wisnu.epoxyexample.feature.home.ui.model

data class ProjectUiModel(
    override val id: String,
    val name: String,
    val description: String,
    val language: String,
    val stars: Int
) : HomeUiItemModel