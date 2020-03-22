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

class HomeViewModel(private val homeInteractor: HomeInteractor) : ViewModel() {

    private val disposables by lazy { CompositeDisposable() }
    private val _state = MutableLiveData<HomeUiState>()
    val state: LiveData<HomeUiState> = _state

    fun loadContent() {
        _state.value = HomeUiState.ShowLoading
        disposables += homeInteractor.getProfileFlowable()
            .doOnNext { _state.postValue(HomeUiState.ProfileResult(it)) }
            .subscribeOn(Schedulers.io())
            .flatMap {
                homeInteractor.getTrendingProjectsFlowable()
                    .doOnNext { _state.postValue(HomeUiState.TrendingProjectResult(it)) }
            }
            .flatMap {
                homeInteractor.getUserProjectsFlowable()
                    .doOnNext { _state.postValue(HomeUiState.ProjectResult(it)) }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _state.value = HomeUiState.ShowContent },
                { _state.value = HomeUiState.ShowError(it) }
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
                    } else {
                        _state.value = HomeUiState.NextProjectResult(it)
                    }
                },
                { _state.value = HomeUiState.ShowError(it) }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}