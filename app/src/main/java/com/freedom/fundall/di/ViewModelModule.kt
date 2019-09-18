package com.freedom.fundall.di

import com.freedom.fundall.ui.auth.AuthViewModel
import org.koin.dsl.module

val viewmodelmodule= module {
    factory { AuthViewModel(get()) }
}