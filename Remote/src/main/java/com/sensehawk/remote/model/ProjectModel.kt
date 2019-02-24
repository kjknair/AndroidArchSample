package com.sensehawk.remote.model

import com.google.gson.annotations.SerializedName

data class ProjectModel(
    val id: String,
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("stargazers_count")
    val starCount: String,
    @SerializedName("created_at")
    val dateCreated: String,
    val owner: OwnerModel
)