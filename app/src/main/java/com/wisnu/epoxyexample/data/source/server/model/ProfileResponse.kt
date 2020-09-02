package com.wisnu.epoxyexample.data.source.server.model

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("bio") val bio: String?,
    @SerializedName("blog") val blog: String?
)