package com.wisnu.epoxyexample.feature.home.domain

import com.spotify.mobius.Effects.effects
import com.spotify.mobius.First
import com.spotify.mobius.First.first
import com.spotify.mobius.Next
import com.spotify.mobius.Next.next
import com.spotify.mobius.Next.noChange
import com.wisnu.epoxyexample.feature.home.domain.model.*

fun init(model: HomeModel): First<HomeModel, HomeEffect> {
    return first(
        model,
        effects(HomeEffect.RefreshData as HomeEffect)
    )
}

fun update(model: HomeModel, event: HomeEvent): Next<HomeModel, HomeEffect> {
    return when (event) {
        HomeEvent.RefreshDataRequested -> onRefreshDataRequested(model)
        is HomeEvent.RefreshDataLoaded -> onRefreshDataLoaded(model, event)
    }
}

private fun onRefreshDataRequested(model: HomeModel): Next<HomeModel, HomeEffect> {
    return next(
        model.copy(loadDataState = LoadDataState.WaitingForData),
        effects(HomeEffect.RefreshData as HomeEffect)
    )
}

private fun onRefreshDataLoaded(
    model: HomeModel,
    event: HomeEvent.RefreshDataLoaded
): Next<HomeModel, HomeEffect> {
    return when (val data = event.loadDataState) {
        is LoadDataState.Failed -> next(model.copy(loadDataState = LoadDataState.Failed(data.type)))
        is LoadDataState.Loaded -> {
            if (isDataEmpty(data)) {
                next(model.copy(loadDataState = LoadDataState.NoResult))
            } else {
                next(model.copy(loadDataState = event.loadDataState))
            }
        }
        else -> noChange()
    }
}

private fun isDataEmpty(data: LoadDataState.Loaded): Boolean {
    return data.myProfileModel.id.isEmpty()
        && data.myProjectModels.isEmpty()
        && data.javaProjectModels.isEmpty()
        && data.kotlinProjectModels.isEmpty()
}