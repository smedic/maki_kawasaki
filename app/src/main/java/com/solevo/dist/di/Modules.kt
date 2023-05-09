package com.solevo.dist.di

import com.solevo.dist.data.network.AuthRepository
import com.solevo.dist.data.network.AuthRepositoryImpl
import com.solevo.dist.data.network.AuthService
import com.solevo.dist.data.network.SharedPrefsRepository
import com.solevo.dist.viewmodel.AuthViewModel
import com.solevo.dist.viewmodel.HomeViewModel
import com.solevo.dist.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AuthService() }
    single<AuthRepository> {
        AuthRepositoryImpl(
            get()
        )
    }
    single { AuthViewModel(get(), get()) }
    single {
        SharedPrefsRepository(
            get()
        )
    }
    viewModel { HomeViewModel() }
    viewModel { SplashViewModel(get()) }

}