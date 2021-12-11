package com.example.teamleadapp.data.repository

import com.example.teamleadapp.data.remote.MockService
import com.example.teamleadapp.data.remote.models.User
import com.example.teamleadapp.di.ApiCaller.apiCall
import com.example.teamleadapp.di.RequestResult

class MockRepositoryImpl(private val mockService: MockService): MockRepository {
    override suspend fun getUsers(): RequestResult<MutableList<User>> =
        apiCall{
            mockService.getUsers()
        }
}