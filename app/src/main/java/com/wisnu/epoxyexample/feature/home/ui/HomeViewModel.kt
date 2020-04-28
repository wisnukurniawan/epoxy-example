package com.wisnu.epoxyexample.feature.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wisnu.epoxyexample.feature.home.domain.HomeInteractor
import com.wisnu.epoxyexample.feature.home.domain.model.HomeUiState
import com.wisnu.epoxyexample.util.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(val homeInteractor: HomeInteractor) : ViewModel() {

    private val disposables by lazy { CompositeDisposable() }
    private val _state = MutableLiveData<HomeUiState>()
    val state: LiveData<HomeUiState> = _state

    fun loadContent() {
        disposables += homeInteractor.getProfileFlowable()
            .doOnSubscribe { _state.postValue(HomeUiState.ShowLoading) }
            .doOnNext { _state.postValue(HomeUiState.ProfileResult(it)) }
            .subscribeOn(Schedulers.io())
            .flatMap {
                homeInteractor.getKotlinTrendingProjectsFlowable()
                    .doOnNext { _state.postValue(HomeUiState.KotlinProjectResult(it)) }
            }
            .flatMap {
                homeInteractor.getJavaTrendingProjectsFlowable()
                    .doOnNext { _state.postValue(HomeUiState.JavaProjectResult(it)) }
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

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}