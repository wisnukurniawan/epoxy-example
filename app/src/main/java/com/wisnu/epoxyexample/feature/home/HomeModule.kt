package com.wisnu.epoxyexample.feature.home

import com.wisnu.epoxyexample.feature.home.domain.HomeUseCase
import com.wisnu.epoxyexample.feature.home.presentation.HomeActivity
import com.wisnu.epoxyexample.feature.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeModule = module(override = true) {
    scope(named<HomeActivity>()) {
        scoped { HomeUseCase(get(), get()) }
        viewModel { HomeViewModel(get()) }
    }
}