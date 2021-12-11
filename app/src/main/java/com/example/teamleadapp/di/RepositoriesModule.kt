package com.example.teamleadapp.di

import com.example.teamleadapp.data.repository.*
import org.koin.dsl.module

val repositoriesModule = module {
    factory<GithubRepository> { GithubRepositoryImpl(get()) }
    factory<DbRepository> { DbRepositoryImpl(get()) }
    factory<MockRepository> { MockRepositoryImpl(get()) }
}