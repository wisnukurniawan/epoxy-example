package com.wisnu.epoxyexample.data

import com.wisnu.epoxyexample.data.server.GithubServerApi
import com.wisnu.epoxyexample.data.server.model.ProjectResponse
import com.wisnu.epoxyexample.domain.entity.Project
import io.reactivex.Flowable

class ProjectRepository(private val githubServerApi: GithubServerApi) {

    fun getUserProjectsFlowable(
        name: String,
        page: Int,
        perPage: Int
    ): Flowable<List<Project>> {
        return githubServerApi.retrieveUserProjectsFlowable(name, page, perPage)
            .map { it.map { item -> mapProjectToVo(item) } }
    }

    fun getProjectsFlowable(
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