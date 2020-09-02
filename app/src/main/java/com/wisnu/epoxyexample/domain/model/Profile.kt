package com.wisnu.epoxyexample.domain.model

data class Profile(
    val id: String,
    val name: String,
    val avatarUrl: String,
    val location: String,
    val bio: String,
    val blog: String
)