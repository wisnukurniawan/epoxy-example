package com.wisnu.epoxyexample.feature.home.domain

import com.wisnu.epoxyexample.data.github.GithubRepository
import com.wisnu.epoxyexample.data.github.db.model.ProfileVo
import com.wisnu.epoxyexample.data.github.db.model.ProjectVo
import com.wisnu.epoxyexample.feature.home.ui.model.ProfileUiModel
import com.wisnu.epoxyexample.feature.home.ui.model.ProjectUiModel
import io.reactivex.Flowable

class HomeInteractor(private val githubRepository: GithubRepository) {

    fun getProfileFlowable(): Flowable<ProfileUiModel> {
        return githubRepository.getProfileFlowable(PROFILE_NAME)
            .map { mapProfileToUiModel(it) }
    }

    fun getUserProjectsFlowable(page: Int = PROJECT_FIRST_PAGE): Flowable<List<ProjectUiModel>> {
        return githubRepository.getUserProjectsFlowable(
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

    fun getKotlinTrendingProjectsFlowable(): Flowable<List<ProjectUiModel>> {
        return githubRepository.getProjectsFlowable(
            TRENDING_KOTLIN_PROJECT_QUERY,
            TRENDING_PROJECT_SORT,
            TRENDING_PROJECT_ORDER
        )
            .map {
                it.take(20)
                    .map { item -> mapProjectToUiModel(item) }
            }
    }

    fun getJavaTrendingProjectsFlowable(): Flowable<List<ProjectUiModel>> {
        return githubRepository.getProjectsFlowable(
            TRENDING_JAVA_PROJECT_QUERY,
            TRENDING_PROJECT_SORT,
            TRENDING_PROJECT_ORDER
        )
            .map {
                it.take(20)
                    .map { item -> mapProjectToUiModel(item) }
            }
    }

    private fun mapProfileToUiModel(type: ProfileVo): ProfileUiModel {
        return ProfileUiModel(
            type.id,
            type.name,
            type.avatarUrl,
            type.location,
            type.bio,
            type.blog
        )
    }

    private fun mapProjectToUiModel(type: ProjectVo): ProjectUiModel {
        return ProjectUiModel(
            type.id,
            type.name,
            type.description,
            type.language,
            type.stargazersCount
        )
    }

    companion object {
        private const val PROFILE_NAME = "wisnukurniawan"
        private const val PROJECT_FIRST_PAGE = 1
        private const val PROJECT_PER_PAGE = Int.MAX_VALUE

        private const val TRENDING_KOTLIN_PROJECT_QUERY = "language:kotlin"
        private const val TRENDING_JAVA_PROJECT_QUERY = "language:java"
        private const val TRENDING_PROJECT_ORDER = "desc"
        private const val TRENDING_PROJECT_SORT = "stars"
    }

}