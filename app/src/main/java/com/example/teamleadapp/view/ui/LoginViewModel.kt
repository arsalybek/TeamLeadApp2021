package com.example.teamleadapp.view.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamleadapp.data.remote.models.Status
import com.example.teamleadapp.data.remote.models.User
import com.example.teamleadapp.data.repository.MockRepository
import com.example.teamleadapp.di.CoroutineProvider
import com.example.teamleadapp.di.UiCaller
import com.example.teamleadapp.di.UiCallerImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class LoginViewModel(private val mockRepository: MockRepository,
                     private val uiCaller: UiCaller = UiCallerImpl(
                         CoroutineScope(
                             SupervisorJob() +
                             CoroutineProvider().IO), CoroutineProvider()
                     )
): ViewModel(), UiCaller by uiCaller {

    val authed = MutableLiveData<Boolean>()

    fun checkUser(username: String, password: String){
        statusLiveData.value = Status.SHOW_LOADING
        makeRequest({
            mockRepository.getUsers()
        }){
            unwrap(it) {
                statusLiveData.value = Status.HIDE_LOADING
                authed.value = it.contains(User(username, password))
            }
        }
    }
}
