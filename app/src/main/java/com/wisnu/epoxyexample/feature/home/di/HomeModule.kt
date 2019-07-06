package com.wisnu.epoxyexample.feature.home.di

import com.wisnu.epoxyexample.feature.home.data.HomeRepositoryImpl
import com.wisnu.epoxyexample.feature.home.domain.interactor.GetProfile
import com.wisnu.epoxyexample.feature.home.domain.interactor.GetProject
import com.wisnu.epoxyexample.feature.home.domain.mapper.ProfileEntityMapper
import com.wisnu.epoxyexample.feature.home.domain.mapper.ProjectEntityMapper
import com.wisnu.epoxyexample.feature.home.domain.repository.HomeRepository
import com.wisnu.epoxyexample.feature.home.ui.HomeActivity
import com.wisnu.epoxyexample.feature.home.ui.HomeViewModel
import com.wisnu.epoxyexample.feature.home.ui.mapper.ProfileMapper
import com.wisnu.epoxyexample.feature.home.ui.mapper.ProjectMapper
import com.wisnu.epoxyexample.util.ServerModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeModule = module(override = true) {
  scope(named<HomeActivity>()) {
    scoped { ProfileEntityMapper() }
    scoped { ProjectEntityMapper() }
    scoped<HomeRepository> {
      HomeRepositoryImpl(get(named(ServerModule.GITHUB_SERVICE)), get(), get())
    }

    scoped { GetProfile(get()) }
    scoped { GetProject(get()) }
    scoped { ProfileMapper() }
    scoped { ProjectMapper() }
    viewModel { HomeViewModel(get(), get(), get(), get()) }
  }
}