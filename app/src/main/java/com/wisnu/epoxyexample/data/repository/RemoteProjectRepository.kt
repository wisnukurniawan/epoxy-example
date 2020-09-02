package com.wisnu.epoxyexample.data.repository

import com.wisnu.epoxyexample.data.server.GithubServerApi
import com.wisnu.epoxyexample.data.server.model.ProjectResponse
import com.wisnu.epoxyexample.domain.repository.ProjectRepository
import com.wisnu.epoxyexample.domain.model.Project
import io.reactivex.Flowable

class RemoteProjectRepository(private val githubServerApi: GithubServerApi) : ProjectRepository {

    override fun getUserProjectsFlowable(
        name: String,
        page: Int,
        perPage: Int
    ): Flowable<List<Project>> {
        return githubServerApi.retrieveUserProjectsFlowable(name, page, perPage)
            .map { it.map { item -> mapProjectToVo(item) } }
    }

    override fun getProjectsFlowable(
        query: String,
        sort: String,
        order: String
    ): Flowable<List<Project>> {
        return githubServerApi.retrieveProjectsFlowable(query, sort, order)
            .map { it.items }
            .map { it.map { item -> mapProjectToVo(item) } }
    }

    private fun mapProjectToVo(type: ProjectResponse): Project {
        return Project(
            type.id ?: "",
            type.name ?: "",
            type.description ?: "",
            type.language ?: "",
            type.stargazersCount ?: 0,
            type.issues ?: 0
        )
    }

}