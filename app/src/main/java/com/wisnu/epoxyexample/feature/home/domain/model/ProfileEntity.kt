package com.wisnu.epoxyexample.feature.home.domain.model


data class ProfileEntity(
    val id: String,
    val name: String,
    val avatarUrl: String,
    val location: String,
    val bio: String,
    val blog: String
)