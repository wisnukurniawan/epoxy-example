package com.wisnu.epoxyexample.presentation.home.domain

import com.wisnu.epoxyexample.data.ProfileRepository
import com.wisnu.epoxyexample.domain.entity.Profile
import com.wisnu.epoxyexample.presentation.home.model.ProfileUiModel
import io.reactivex.Flowable

class GetProfileUseCase(private val profileRepository: ProfileRepository) {

    fun getProfileFlowable(): Flowable<ProfileUiModel> {
        return profileRepository.getProfileFlowable(PROFILE_NAME)
            .map { mapProfileToUiModel(it) }
    }

    private fun mapProfileToUiModel(type: Profile): ProfileUiModel {
        return ProfileUiModel(
            type.id,
            type.name,
            type.avatarUrl,
            type.location,
            type.bio,
            type.blog
        )
    }

    companion object {
        private const val PROFILE_NAME = "wisnukurniawan"
    }

}