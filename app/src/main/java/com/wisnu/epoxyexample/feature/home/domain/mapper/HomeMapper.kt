package com.wisnu.epoxyexample.feature.home.domain.mapper

import com.wisnu.epoxyexample.core.server.github.model.ProfileResponse
import com.wisnu.epoxyexample.core.server.github.model.ProjectResponse
import com.wisnu.epoxyexample.feature.home.domain.model.ProfileEntity
import com.wisnu.epoxyexample.feature.home.domain.model.ProjectEntity

class ProfileEntityMapper {
  fun mapFromResponse(type: ProfileResponse): ProfileEntity {
    return ProfileEntity(
        type.id ?: "",
        type.name ?: "",
        type.avatarUrl ?: "",
        type.location ?: "",
        type.bio ?: "",
        type.blog ?: ""
    )
  }
}

class ProjectEntityMapper {
  fun mapFromResponse(type: ProjectResponse): ProjectEntity {
    return ProjectEntity(
        type.id ?: "",
        type.name ?: "",
        type.description ?: "",
        type.language ?: "",
        type.stargazersCount ?: 0
    )
  }
}