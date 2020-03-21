package com.wisnu.epoxyexample.feature.home.ui.model

data class TrendingProjectUiModel(
    val id: String,
    val name: String,
    val description: String,
    val language: String,
    val stars: Int
)

data class TrendingProjectUiModelWrapper(
    override val id: String,
    val projects: List<TrendingProjectUiModel>
) : HomeUiItemModel