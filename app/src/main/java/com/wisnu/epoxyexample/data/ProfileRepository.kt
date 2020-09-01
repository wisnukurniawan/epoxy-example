package com.wisnu.epoxyexample.data

import com.wisnu.epoxyexample.data.server.GithubServerApi
import com.wisnu.epoxyexample.data.server.model.ProfileResponse
import com.wisnu.epoxyexample.domain.entity.Profile
import io.reactivex.Flowable

class ProfileRepository(private val githubServerApi: GithubServerApi) {

    fun getProfileFlowable(name: String): Flowable<Profile> {
        return githubServerApi.retrieveProfileFlowable(name)
            .map { mapProfileToVo(it) }
    }

    private fun mapProfileToVo(type: ProfileResponse): Profile {
        return Profile(
            type.id ?: "",
            type.name ?: "",
            type.avatarUrl ?: "",
            type.location ?: "",
            type.bio ?: "",
            type.blog ?: ""
        )
    }

}