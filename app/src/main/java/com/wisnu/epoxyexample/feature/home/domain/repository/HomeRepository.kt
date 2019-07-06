package com.wisnu.epoxyexample.feature.home.domain.repository

import com.wisnu.epoxyexample.feature.home.domain.model.ProfileEntity
import com.wisnu.epoxyexample.feature.home.domain.model.ProjectEntity
import io.reactivex.Flowable

interface HomeRepository {

  fun getProfileFlowable(name: String): Flowable<ProfileEntity>

  fun getProjectsFlowable(
      name: String,
      page: Int,
      perPage: Int
  ): Flowable<List<ProjectEntity>>

}