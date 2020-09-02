package com.wisnu.epoxyexample.data.repository

import com.wisnu.epoxyexample.data.source.server.GithubServerApi
import com.wisnu.epoxyexample.data.source.server.model.ProfileResponse
import com.wisnu.epoxyexample.domain.repository.ProfileRepository
import com.wisnu.epoxyexample.domain.model.Profile
import io.reactivex.Flowable

class RemoteProfileRepository(private val githubServerApi: GithubServerApi) : ProfileRepository {

    override fun getProfileFlowable(name: String): Flowable<Profile> {
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