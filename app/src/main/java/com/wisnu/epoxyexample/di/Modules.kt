package com.wisnu.epoxyexample.di

import com.wisnu.epoxyexample.BuildConfig
import com.wisnu.epoxyexample.feature.home.di.homeModule

val modules = listOf(
    serverModule(BuildConfig.GITHUB_BASE_URL),
    homeModule
)