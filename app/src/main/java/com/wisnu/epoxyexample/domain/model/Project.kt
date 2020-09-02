package com.wisnu.epoxyexample.domain.model

data class Project(
    val id: String,
    val name: String,
    val description: String,
    val language: String,
    val stargazersCount: Int,
    val issues: Int
)