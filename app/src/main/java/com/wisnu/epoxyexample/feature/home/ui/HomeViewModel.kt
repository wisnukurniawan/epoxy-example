package com.wisnu.epoxyexample.feature.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wisnu.epoxyexample.feature.home.domain.HomeInteractor
import com.wisnu.epoxyexample.feature.home.ui.model.HomeUiState
import com.wisnu.epoxyexample.util.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

class HomeViewModel(private val homeInteractor: HomeInteractor) : ViewModel() {

    private val disposables by lazy { CompositeDisposable() }
    private val _state = MutableLiveData<HomeUiState>()
    val state: LiveData<HomeUiState> = _state

    fun loadData() {
        _state.value = HomeUiState.ShowLoading
        disposables += homeInteractor.getProfileFlowable()
            .subscribeOn(Schedulers.io())
            .flatMap { profile ->
                homeInteractor.getTrendingProjectsFlowable()
                    .map { persistentListOf(profile) + it }
            }
            .flatMap { projects ->
                homeInteractor.getUserProjectsFlowable()
                    .map { projects.toPersistentList() + it }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _state.value = HomeUiState.HideLoading
                    _state.value = HomeUiState.Result(it)
                },
                {
                    _state.value = HomeUiState.HideLoading
                    _state.value = HomeUiState.Error(it)
                }
            )
    }

    fun loadNextProjects(page: Int) {
        _state.value = HomeUiState.ShowLoadMore
        disposables += homeInteractor.getUserProjectsFlowable(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.isEmpty()) {
                        _state.value = HomeUiState.HideLoadMore
                    }
                    _state.value = HomeUiState.NextResult(it)
                },
                { _state.value = HomeUiState.Error(it) }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}