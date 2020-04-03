package com.wisnu.epoxyexample.data.github.db.model

data class ProjectVo(
    val id: String,
    val name: String,
    val description: String,
    val language: String,
    val stargazersCount: Int,
    val issues: Int
)