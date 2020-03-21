package com.wisnu.epoxyexample.data.github.di

import com.wisnu.epoxyexample.data.github.GithubRepository
import com.wisnu.epoxyexample.util.ServerModule
import org.koin.core.qualifier.named
import org.koin.dsl.module

val githubRepositoryModule = module(override = true) {
    single { GithubRepository(get(named(ServerModule.GITHUB_SERVICE))) }
}