package com.wisnu.epoxyexample.data.github

import com.wisnu.epoxyexample.data.github.db.model.ProfileVo
import com.wisnu.epoxyexample.data.github.db.model.ProjectVo
import com.wisnu.epoxyexample.data.github.server.GithubServerApi
import com.wisnu.epoxyexample.data.github.server.model.ProfileResponse
import com.wisnu.epoxyexample.data.github.server.model.ProjectResponse
import io.reactivex.Flowable

class GithubRepository(private val githubServerApi: GithubServerApi) {

    fun getProfileFlowable(name: String): Flowable<ProfileVo> {
        return githubServerApi.retrieveProfileFlowable(name)
            .map { mapProfileToVo(it) }
    }

    fun getUserProjectsFlowable(
        name: String,
        page: Int,
        perPage: Int
    ): Flowable<List<ProjectVo>> {
        return githubServerApi.retrieveUserProjectsFlowable(name, page, perPage)
            .map { it.map { item -> mapProjectToVo(item) } }
    }

    fun getProjectsFlowable(
        query: String,
        sort: String,
        order: String
    ): Flowable<List<ProjectVo>> {
        return githubServerApi.retrieveProjectsFlowable(query, sort, order)
            .map { it.items }
            .map { it.map { item -> mapProjectToVo(item) } }
    }

    private fun mapProfileToVo(type: ProfileResponse): ProfileVo {
        return ProfileVo(
            type.id ?: "",
            type.name ?: "",
            type.avatarUrl ?: "",
            type.location ?: "",
            type.bio ?: "",
            type.blog ?: ""
        )
    }

    private fun mapProjectToVo(type: ProjectResponse): ProjectVo {
        return ProjectVo(
            type.id ?: "",
            type.name ?: "",
            type.description ?: "",
            type.language ?: "",
            type.stargazersCount ?: 0,
            type.issues ?: 0
        )
    }

}