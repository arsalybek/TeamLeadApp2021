package com.example.teamleadapp.view.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.teamleadapp.data.remote.models.GitHubCommitResponse
import com.example.teamleadapp.data.repository.GithubRepository
import com.example.teamleadapp.di.CoroutineProvider
import com.example.teamleadapp.di.UiCaller
import com.example.teamleadapp.di.UiCallerImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class GithubViewModel(private val githubRepository: GithubRepository,
                      private val uiCaller: UiCaller = UiCallerImpl(CoroutineScope(SupervisorJob() +
                              CoroutineProvider().IO), CoroutineProvider())): ViewModel(), UiCaller by uiCaller {

    val commitsLiveData = MutableLiveData<MutableList<GitHubCommitResponse>>()

    private val _liveDataTest = MutableLiveData<String>()
    val liveData1: LiveData<String> = _liveDataTest

    fun getEmployeeCommits(username: String, repoName: String){
        makeRequest({
            githubRepository.getUserCommits(username, repoName)
        }){
            unwrap(it) {
                commitsLiveData.value = it
            }
        }
    }

    val liveData2 = _liveDataTest.map { it.toUpperCase() }

    fun setNewValue(newValue: String) {
        _liveDataTest.value = newValue
    }
}