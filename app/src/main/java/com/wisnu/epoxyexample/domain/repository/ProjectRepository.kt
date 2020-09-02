package com.wisnu.epoxyexample.domain.repository

import com.wisnu.epoxyexample.domain.model.Project
import io.reactivex.Flowable

interface ProjectRepository {
    fun getUserProjectsFlowable(
        name: String,
        page: Int,
        perPage: Int
    ): Flowable<List<Project>>

    fun getProjectsFlowable(
        query: String,
        sort: String,
        order: String
    ): Flowable<List<Project>>
}