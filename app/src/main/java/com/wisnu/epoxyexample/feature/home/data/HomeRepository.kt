package com.wisnu.epoxyexample.feature.home.data

import com.wisnu.epoxyexample.core.server.github.GithubServerApi
import com.wisnu.epoxyexample.core.server.github.model.ProfileResponse
import com.wisnu.epoxyexample.core.server.github.model.ProjectResponse
import io.reactivex.Flowable

class HomeRepository(
    private val githubServerApi: GithubServerApi
) {

    fun getProfileFlowable(name: String): Flowable<ProfileResponse> {
        return githubServerApi.retrieveProfileFlowable(name)
    }

    fun getProjectsFlowable(
        name: String, page: Int,
        perPage: Int
    ): Flowable<List<ProjectResponse>> {
        return githubServerApi.retrieveProjectsFlowable(name, page, perPage)
    }

}