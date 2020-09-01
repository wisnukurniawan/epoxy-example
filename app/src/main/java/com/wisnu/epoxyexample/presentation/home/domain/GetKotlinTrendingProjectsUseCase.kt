package com.wisnu.epoxyexample.presentation.home.domain

import com.wisnu.epoxyexample.data.ProjectRepository
import com.wisnu.epoxyexample.domain.entity.Project
import com.wisnu.epoxyexample.presentation.home.model.ProjectUiModel
import io.reactivex.Flowable

class GetKotlinTrendingProjectsUseCase(private val projectRepository: ProjectRepository) {

    fun getKotlinTrendingProjectsFlowable(): Flowable<List<ProjectUiModel>> {
        return projectRepository.getProjectsFlowable(
            TRENDING_KOTLIN_PROJECT_QUERY,
            TRENDING_PROJECT_SORT,
            TRENDING_PROJECT_ORDER
        )
            .map {
                it.take(20)
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
        private const val TRENDING_KOTLIN_PROJECT_QUERY = "language:kotlin"
        private const val TRENDING_PROJECT_ORDER = "desc"
        private const val TRENDING_PROJECT_SORT = "stars"
    }

}