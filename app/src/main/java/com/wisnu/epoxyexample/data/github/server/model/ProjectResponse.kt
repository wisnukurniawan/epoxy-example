package com.wisnu.epoxyexample.data.github.server.model

import com.google.gson.annotations.SerializedName

data class ProjectResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("stargazers_count") val stargazersCount: Int?,
    @SerializedName("open_issues_count") val issues: Int?
)