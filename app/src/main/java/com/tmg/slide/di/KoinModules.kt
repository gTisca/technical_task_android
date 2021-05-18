/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.di

import com.tmg.slide.BuildConfig
import com.tmg.slide.data.network.api.DataApi
import com.tmg.slide.data.network.config.BASE_URL
import com.tmg.slide.data.network.config.createHttpClient
import com.tmg.slide.data.network.config.createNetworkClient
import com.tmg.slide.data.repositoryImpl.UserRepositoryImpl
import com.tmg.slide.data.utils.SlideGson
import com.tmg.slide.domain.repository.UserRepository
import com.tmg.slide.domain.usecase.UserUseCase
import com.tmg.slide.presentation.main.MainActivityPresenter
import org.koin.core.module.Module
import org.koin.dsl.module

private val presenterModule: Module = module {
    factory { MainActivityPresenter(userUseCase = get()) }
}

private val useCaseModule: Module = module {
    factory { UserUseCase(userRepository = get()) }
}

private val repositoryModule: Module = module {
    factory<UserRepository> { UserRepositoryImpl(dataApi = get()) }
}

private val networkModule: Module = module {
    single { createNetworkClient(BASE_URL, SlideGson.gsonInstance, createHttpClient(BuildConfig.DEBUG)).create(DataApi::class.java) }
}

val appModules = listOf(presenterModule, useCaseModule, repositoryModule, networkModule)
