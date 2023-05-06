package com.example.myapplication.di

import com.example.myapplication.data.network.AuthRepository
import com.example.myapplication.data.network.AuthRepositoryImpl
import com.example.myapplication.data.network.AuthService
import com.example.myapplication.viewmodel.AuthViewModel
import com.example.myapplication.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AuthService() }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single { AuthViewModel(get()) }
    viewModel { HomeViewModel() }
}