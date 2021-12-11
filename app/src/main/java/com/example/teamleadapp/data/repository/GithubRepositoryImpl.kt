package com.example.teamleadapp.data.repository

import com.example.teamleadapp.data.remote.GithubService
import com.example.teamleadapp.data.remote.models.GitHubCommitResponse
import com.example.teamleadapp.data.repository.GithubRepository
import com.example.teamleadapp.di.ApiCaller
import com.example.teamleadapp.di.RequestResult
import com.example.teamleadapp.di.CoroutineCaller

class GithubRepositoryImpl(private val githubService: GithubService) : GithubRepository, CoroutineCaller by ApiCaller {
    override suspend fun getUserCommits(
        username: String,
        repoName: String
    ): RequestResult<MutableList<GitHubCommitResponse>> =
        apiCall {
            githubService.getCommits(username, repoName)
        }
}