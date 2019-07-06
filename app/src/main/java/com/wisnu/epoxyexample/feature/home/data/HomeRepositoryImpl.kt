package com.wisnu.epoxyexample.feature.home.data

import com.wisnu.epoxyexample.core.server.github.GithubServerApi
import com.wisnu.epoxyexample.feature.home.domain.mapper.ProfileEntityMapper
import com.wisnu.epoxyexample.feature.home.domain.mapper.ProjectEntityMapper
import com.wisnu.epoxyexample.feature.home.domain.model.ProfileEntity
import com.wisnu.epoxyexample.feature.home.domain.model.ProjectEntity
import com.wisnu.epoxyexample.feature.home.domain.repository.HomeRepository
import io.reactivex.Flowable

class HomeRepositoryImpl(
    private val githubServerApi: GithubServerApi,
    private val profileEntityMapper: ProfileEntityMapper,
    private val projectEntityMapper: ProjectEntityMapper
) : HomeRepository {

  override fun getProfileFlowable(name: String): Flowable<ProfileEntity> {
    return githubServerApi.retrieveProfileFlowable(name)
        .map { profileEntityMapper.mapFromResponse(it) }
  }

  override fun getProjectsFlowable(name: String, page: Int,
      perPage: Int): Flowable<List<ProjectEntity>> {
    return githubServerApi.retrieveProjectsFlowable(name, page, perPage)
        .map { it.map { item -> projectEntityMapper.mapFromResponse(item) } }
  }

}