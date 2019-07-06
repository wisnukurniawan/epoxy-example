package com.wisnu.epoxyexample.feature.home.domain.interactor

import com.wisnu.epoxyexample.feature.home.domain.model.ProfileEntity
import com.wisnu.epoxyexample.feature.home.domain.repository.HomeRepository
import io.reactivex.Flowable

class GetProfile(private val homeRepository: HomeRepository) {

  operator fun invoke(name: String): Flowable<ProfileEntity> {
    return homeRepository.getProfileFlowable(name)
  }

}