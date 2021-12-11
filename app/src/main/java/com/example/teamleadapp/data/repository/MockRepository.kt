package com.example.teamleadapp.data.repository

import com.example.teamleadapp.data.remote.models.User
import com.example.teamleadapp.di.RequestResult

interface MockRepository {
    suspend fun getUsers(): RequestResult<MutableList<User>>
}