package com.example.teamleadapp.data.remote

import com.example.teamleadapp.data.remote.models.GitHubCommitResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("repos/{username}/{repoName}/commits")
    suspend fun getCommits(@Path("username") username: String, @Path("repoName") repoName: String): MutableList<GitHubCommitResponse>
}