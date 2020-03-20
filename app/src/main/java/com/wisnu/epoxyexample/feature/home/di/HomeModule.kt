package com.wisnu.epoxyexample.feature.home.di

import com.wisnu.epoxyexample.feature.home.data.HomeRepository
import com.wisnu.epoxyexample.feature.home.domain.HomeInteractor
import com.wisnu.epoxyexample.feature.home.ui.HomeActivity
import com.wisnu.epoxyexample.feature.home.ui.HomeViewModel
import com.wisnu.epoxyexample.util.ServerModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeModule = module(override = true) {
    scope(named<HomeActivity>()) {
        scoped { HomeRepository(get(named(ServerModule.GITHUB_SERVICE))) }
        scoped { HomeInteractor(get()) }
        viewModel { HomeViewModel(get()) }
    }
}