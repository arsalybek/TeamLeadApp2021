package com.example.teamleadapp.data.repository

import com.example.teamleadapp.data.remote.models.GitHubCommitResponse
import com.example.teamleadapp.di.RequestResult


interface GithubRepository {
    suspend fun getUserCommits(username: String, repoName: String): RequestResult<MutableList<GitHubCommitResponse>>
}