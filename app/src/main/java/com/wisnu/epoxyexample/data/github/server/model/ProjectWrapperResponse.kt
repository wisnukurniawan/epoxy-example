package com.wisnu.epoxyexample.data.github.server.model

import com.google.gson.annotations.SerializedName

data class ProjectWrapperResponse(
    @SerializedName("total_count") val totalCount: Int?,
    @SerializedName("incomplete_results") val incompleteResults: Boolean?,
    @SerializedName("items") val items: List<ProjectResponse>?
)