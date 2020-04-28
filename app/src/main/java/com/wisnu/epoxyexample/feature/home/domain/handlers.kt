package com.wisnu.epoxyexample.feature.home.domain

import com.wisnu.epoxyexample.feature.home.domain.model.*
import com.wisnu.epoxyexample.util.SubtypeEffectHandlerBuilder
import io.reactivex.Flowable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers
import kotlinx.collections.immutable.toPersistentList

fun handleEffect(homeInteractor: HomeInteractor): ObservableTransformer<HomeEffect, HomeEvent> {
    return SubtypeEffectHandlerBuilder<HomeEffect, HomeEvent>()
        .addTransformer(refreshDataHandler(homeInteractor))
        .build()
}

private fun refreshDataHandler(homeInteractor: HomeInteractor): ObservableTransformer<HomeEffect.RefreshData, HomeEvent> {
    return ObservableTransformer {
        it.observeOn(Schedulers.io())
            .flatMapSingle {
                Flowable.zip<ProfileUiModel, List<ProjectUiModel>, List<ProjectUiModel>, List<ProjectUiModel>, HomeEvent.RefreshDataLoaded>(
                    homeInteractor.getProfileFlowable(),
                    homeInteractor.getUserProjectsFlowable(),
                    homeInteractor.getKotlinTrendingProjectsFlowable(),
                    homeInteractor.getJavaTrendingProjectsFlowable(),
                    Function4 { t1, t2, t3, t4 ->
                        HomeEvent.RefreshDataLoaded(
                            LoadDataState.Loaded(
                                t1,
                                t2.toPersistentList(),
                                t3.toPersistentList(),
                                t4.toPersistentList()
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
