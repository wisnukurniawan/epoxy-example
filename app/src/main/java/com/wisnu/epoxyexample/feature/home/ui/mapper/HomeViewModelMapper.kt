package com.wisnu.epoxyexample.feature.home.ui.mapper

import com.wisnu.epoxyexample.feature.home.domain.model.ProfileEntity
import com.wisnu.epoxyexample.feature.home.domain.model.ProjectEntity
import com.wisnu.epoxyexample.feature.home.ui.view.ProfileView
import com.wisnu.epoxyexample.feature.home.ui.view.ProjectView

class ProfileMapper {
  fun mapFromEntity(type: ProfileEntity): ProfileView.Model {
    return ProfileView.Model(
        type.id,
        type.name,
        type.avatarUrl,
        type.location,
        type.bio,
        type.blog
    )
  }
}

class ProjectMapper {
  fun mapFromEntity(type: ProjectEntity): ProjectView.Model {
    return ProjectView.Model(
        type.id,
        type.name,
        type.description,
        type.language,
        type.stars
    )
  }
}