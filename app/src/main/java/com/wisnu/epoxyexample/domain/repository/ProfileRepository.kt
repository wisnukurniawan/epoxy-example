package com.wisnu.epoxyexample.domain.repository

import com.wisnu.epoxyexample.domain.model.Profile
import io.reactivex.Flowable

interface ProfileRepository {
    fun getProfileFlowable(name: String): Flowable<Profile>
}