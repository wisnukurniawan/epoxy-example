package com.wisnu.epoxyexample.presentation.home.domain

import com.wisnu.epoxyexample.data.ProjectRepository
import com.wisnu.epoxyexample.domain.entity.Project
import com.wisnu.epoxyexample.presentation.home.model.ProjectUiModel
import io.reactivex.Flowable

class GetUserProjectsUseCase(private val projectRepository: ProjectRepository) {

    fun getUserProjectsFlowable(page: Int = PROJECT_FIRST_PAGE): Flowable<List<ProjectUiModel>> {
        return projectRepository.getUserProjectsFlowable(
            PROFILE_NAME,
            page,
            PROJECT_PER_PAGE
        )
            .map {
                it.sortedByDescending { it.stargazersCount }
                    .take(20)
                    .map { item -> mapProjectToUiModel(item) }
            }
    }

    private fun mapProjectToUiModel(type: Project): ProjectUiModel {
        return ProjectUiModel(
            type.id,
            type.name,
            type.description,
            type.language,
            type.stargazersCount,
            type.issues
        )
    }

    companion object {
        private const val PROFILE_NAME = "wisnukurniawan"
        private const val PROJECT_FIRST_PAGE = 1
        private const val PROJECT_PER_PAGE = 100
    }

}