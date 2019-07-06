package com.wisnu.epoxyexample.feature.home.domain.model


data class ProjectEntity(
    val id: String,
    val name: String,
    val description: String,
    val language: String,
    val stars: Int
)