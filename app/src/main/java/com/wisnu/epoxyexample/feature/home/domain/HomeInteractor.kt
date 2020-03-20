package com.wisnu.epoxyexample.feature.home.domain

import com.wisnu.epoxyexample.core.server.github.model.ProfileResponse
import com.wisnu.epoxyexample.core.server.github.model.ProjectResponse
import com.wisnu.epoxyexample.feature.home.data.HomeRepository
import com.wisnu.epoxyexample.feature.home.ui.model.ProfileUiModel
import com.wisnu.epoxyexample.feature.home.ui.model.ProjectUiModel
import io.reactivex.Flowable

class HomeInteractor(private val homeRepository: HomeRepository) {

    fun getProfileFlowable(name: String): Flowable<ProfileUiModel> {
        return homeRepository.getProfileFlowable(name)
            .map { mapProfileToUiModel(it) }
    }

    fun getProjectsFlowable(
        name: String, page: Int,
        perPage: Int
    ): Flowable<List<ProjectUiModel>> {
        return homeRepository.getProjectsFlowable(name, page, perPage)
            .map { it.map { item -> mapProjectToUiModel(item) } }
    }

    private fun mapProfileToUiModel(type: ProfileResponse): ProfileUiModel {
        return ProfileUiModel(
            type.id ?: "",
            type.name ?: "",
            type.avatarUrl ?: "",
            type.location ?: "",
            type.bio ?: "",
            type.blog ?: ""
        )
    }

    private fun mapProjectToUiModel(type: ProjectResponse): ProjectUiModel {
        return ProjectUiModel(
            type.id ?: "",
            type.name ?: "",
            type.description ?: "",
            type.language ?: "",
            type.stargazersCount ?: 0
        )
    }

}