package com.example.teamleadapp.di

import com.example.teamleadapp.view.ui.GithubViewModel
import com.example.teamleadapp.view.ui.LoginViewModel
import com.example.teamleadapp.view.ui.empList.EmpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { GithubViewModel(get()) }
    viewModel { EmpViewModel(get()) }
    viewModel { LoginViewModel(get())}
}