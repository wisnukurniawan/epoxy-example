package com.wisnu.epoxyexample.feature.home.domain.interactor

import com.wisnu.epoxyexample.feature.home.domain.model.ProjectEntity
import com.wisnu.epoxyexample.feature.home.domain.repository.HomeRepository
import io.reactivex.Flowable

class GetProject(private val homeRepository: HomeRepository) {

  operator fun invoke(
      name: String,
      page: Int,
      perPage: Int
  ): Flowable<List<ProjectEntity>> {
    return homeRepository.getProjectsFlowable(name, page, perPage)
  }

}