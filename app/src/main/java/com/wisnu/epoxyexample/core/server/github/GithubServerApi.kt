package com.wisnu.epoxyexample.core.server.github

import com.wisnu.epoxyexample.core.server.github.model.ProfileResponse
import com.wisnu.epoxyexample.core.server.github.model.ProjectResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubServerApi {

  @GET("users/{name}")
  fun retrieveProfileFlowable(@Path("name") name: String): Flowable<ProfileResponse>

  @GET("users/{name}/repos")
  fun retrieveProjectsFlowable(
      @Path("name") name: String,
      @Query("page") page: Int,
      @Query("per_page") perPage: Int
  ): Flowable<List<ProjectResponse>>

}