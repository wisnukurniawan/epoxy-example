package com.wisnu.epoxyexample.presentation.home

import com.wisnu.epoxyexample.presentation.home.domain.HomeUseCase
import com.wisnu.epoxyexample.presentation.home.ui.HomeActivity
import com.wisnu.epoxyexample.presentation.home.ui.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeModule = module(override = true) {
    scope(named<HomeActivity>()) {
        scoped { HomeUseCase(get(), get()) }
        viewModel { HomeViewModel(get()) }
    }
}