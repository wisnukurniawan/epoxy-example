package com.wisnu.epoxyexample.feature.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wisnu.epoxyexample.feature.home.domain.interactor.GetProfile
import com.wisnu.epoxyexample.feature.home.domain.interactor.GetProject
import com.wisnu.epoxyexample.feature.home.ui.mapper.ProfileMapper
import com.wisnu.epoxyexample.feature.home.ui.mapper.ProjectMapper
import com.wisnu.epoxyexample.feature.home.ui.model.HomeItemModel
import com.wisnu.epoxyexample.feature.home.ui.model.HomeState
import com.wisnu.epoxyexample.util.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val getProfile: GetProfile,
    private val getProject: GetProject,
    private val profileMapper: ProfileMapper,
    private val projectMapper: ProjectMapper
) : ViewModel() {

  private val disposables by lazy { CompositeDisposable() }
  private val _state = MutableLiveData<HomeState>()
  val state: LiveData<HomeState> = _state

  fun loadProfile() {
    disposables += getProfile(PROFILE_NAME)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext { _state.value = HomeState.ShowLoading }
        .subscribe(
            {
              val profile = profileMapper.mapFromEntity(it)
              loadProjects(profile)
            },
            {
              _state.value = HomeState.Error(it)
            }
        )
  }

  private fun loadProjects(homeItemModel: HomeItemModel) {
    disposables += getProject(PROFILE_NAME, PROJECT_FIRST_PAGE, PROJECT_PER_PAGE)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            {
              val list = mutableListOf<HomeItemModel>()
              list.add(homeItemModel)
              it.forEach { item -> list.add(projectMapper.mapFromEntity(item)) }
              _state.value = HomeState.Result(list)
            },
            {
              _state.value = HomeState.Error(it)
            }
        )
  }

  fun loadNextProjects(page: Int) {
    disposables += getProject(PROFILE_NAME, page, PROJECT_PER_PAGE)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            {
              val list = mutableListOf<HomeItemModel>()
              it.forEach { item -> list.add(projectMapper.mapFromEntity(item)) }
              _state.value = HomeState.NextResult(list)
            },
            {
              _state.value = HomeState.Error(it)
            }
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