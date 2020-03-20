package com.wisnu.epoxyexample.feature.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wisnu.epoxyexample.feature.home.domain.HomeInteractor
import com.wisnu.epoxyexample.feature.home.ui.model.HomeUiItemModel
import com.wisnu.epoxyexample.feature.home.ui.model.HomeUiState
import com.wisnu.epoxyexample.util.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val homeInteractor: HomeInteractor
) : ViewModel() {

    private val disposables by lazy { CompositeDisposable() }
    private val _state = MutableLiveData<HomeUiState>()
    val state: LiveData<HomeUiState> = _state

    fun loadProfile() {
        disposables += homeInteractor.getProfileFlowable(PROFILE_NAME)
            .startWith { _state.value = HomeUiState.ShowLoading }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { loadProjects(it) },
                { _state.value = HomeUiState.Error(it) }
            )
    }

    private fun loadProjects(homeUiItemModel: HomeUiItemModel) {
        disposables += homeInteractor.getProjectsFlowable(
            PROFILE_NAME,
            PROJECT_FIRST_PAGE,
            PROJECT_PER_PAGE
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val list = mutableListOf<HomeUiItemModel>()
                    list.add(homeUiItemModel)
                    list.addAll(it)
                    _state.value = HomeUiState.Result(list)
                },
                { _state.value = HomeUiState.Error(it) }
            )
    }

    fun loadNextProjects(page: Int) {
        disposables += homeInteractor.getProjectsFlowable(PROFILE_NAME, page, PROJECT_PER_PAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _state.value = HomeUiState.NextResult(it) },
                { _state.value = HomeUiState.Error(it) }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    companion object {
        private const val PROFILE_NAME = "wisnukurniawan"
        private const val PROJECT_FIRST_PAGE = 1
        private const val PROJECT_PER_PAGE = 10
    }

}