package com.wisnu.epoxyexample.data.server

import com.wisnu.epoxyexample.data.server.model.ProfileResponse
import com.wisnu.epoxyexample.data.server.model.ProjectResponse
import com.wisnu.epoxyexample.data.server.model.ProjectWrapperResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubServerApi {

    @GET("users/{name}")
    fun retrieveProfileFlowable(@Path("name") name: String): Flowable<ProfileResponse>

    @GET("users/{name}/repos")
    fun retrieveUserProjectsFlowable(
        @Path("name") name: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Flowable<List<ProjectResponse>>

    @GET("search/repositories")
    fun retrieveProjectsFlowable(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("order") order: String
    ): Flowable<ProjectWrapperResponse>

}