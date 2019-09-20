package com.freedom.fundall.di

import com.freedom.fundall.repository.AccountRepository
import com.freedom.fundall.repository.AuthRepository
import com.freedom.fundall.repository.session
import org.koin.dsl.module

val repositoryModel= module {
    factory { AuthRepository(get(),get()) }
    factory { session() }
    factory { AccountRepository(get(),get()) }
}