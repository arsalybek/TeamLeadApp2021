package com.example.teamleadapp.data.remote.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class Committer(
    val date: String
) : Serializable

@Keep
data class GitHubCommit(
    val committer: Committer,
    @SerializedName("message")
    var commitMessage: String
) : Serializable

@Keep
data class GitHubCommitResponse(
    val commit: GitHubCommit
) : Serializable
