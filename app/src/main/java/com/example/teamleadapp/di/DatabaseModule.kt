package com.example.teamleadapp.di

import com.example.teamleadapp.data.local.DbHelper
import org.koin.dsl.module

val databaseModule = module {
    single { DbHelper(get()) }
}