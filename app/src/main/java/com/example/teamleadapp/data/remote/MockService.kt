package com.example.teamleadapp.data.remote

import com.example.teamleadapp.data.remote.models.User
import retrofit2.http.GET

interface MockService {
    @GET("https://61a622718395690017be90da.mockapi.io/team_lead_app/users")
    suspend fun getUsers(): MutableList<User>
}