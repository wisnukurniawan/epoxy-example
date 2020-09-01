package com.wisnu.epoxyexample.presentation.home.domain

import com.wisnu.epoxyexample.presentation.home.model.*
import com.wisnu.epoxyexample.util.SubtypeEffectHandlerBuilder
import io.reactivex.Flowable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers
import kotlinx.collections.immutable.toPersistentList

fun handleEffect(homeUseCase: HomeUseCase): ObservableTransformer<HomeEffect, HomeEvent> {
    return SubtypeEffectHandlerBuilder<HomeEffect, HomeEvent>()
        .addTransformer(refreshDataHandler(homeUseCase))
        .build()
}

private fun refreshDataHandler(homeUseCase: HomeUseCase): ObservableTransformer<HomeEffect.RefreshData, HomeEvent> {
    return ObservableTransformer {
        it.observeOn(Schedulers.io())
            .flatMapSingle {
                Flowable.zip<ProfileUiModel, List<ProjectUiModel>, List<ProjectUiModel>, List<ProjectUiModel>, HomeEvent.RefreshDataLoaded>(
                    homeUseCase.getProfileFlowable(),
                    homeUseCase.getUserProjectsFlowable(),
                    homeUseCase.getKotlinTrendingProjectsFlowable(),
                    homeUseCase.getJavaTrendingProjectsFlowable(),
                    Function4 { profileUiModel, projectUiModels1, projectUiModels2, projectUiModels4 ->
                        HomeEvent.RefreshDataLoaded(
                            LoadDataState.Loaded(
                                profileUiModel,
                                projectUiModels1.toPersistentList(),
                                projectUiModels2.toPersistentList(),
                                projectUiModels4.toPersistentList()
                            )
                        )
                    }
                )
                    .singleOrError()
                    .onErrorReturn {
                        HomeEvent.RefreshDataLoaded(LoadDataState.Failed(FeedbackType.LOADING_ERROR))
                    }
            }
    }
}
