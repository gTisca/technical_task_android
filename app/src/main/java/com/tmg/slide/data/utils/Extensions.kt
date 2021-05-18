/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.data.utils

import com.tmg.slide.domain.models.AppErrors
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Completable.mapErrors(): Completable =
    this.onErrorResumeNext { error ->
        when (error) {
            is AppErrors -> Completable.error(error) //already catch
            is SocketTimeoutException -> Completable.error(AppErrors.NoNetworkException)
            is UnknownHostException -> Completable.error(AppErrors.ServerUnreachableException)
            is HttpException -> Completable.error(AppErrors.HttpCallFailureException(error.message))
            else -> Completable.error(AppErrors.GeneralError)
        }
    }

fun <T> Single<T>.mapErrors(): Single<T> =
    this.onErrorResumeNext { error ->
        when (error) {
            is AppErrors -> Single.error(error) //already catch
            is SocketTimeoutException -> Single.error(AppErrors.NoNetworkException)
            is UnknownHostException -> Single.error(AppErrors.ServerUnreachableException)
            is HttpException -> Single.error(AppErrors.HttpCallFailureException(error.message))
            else -> Single.error(AppErrors.GeneralError)
        }
    }

fun <T : DomainMappable<R>, R> Single<T>.mapToDomain(): Single<R> = this.map { it.asDomain() }
fun <T : DomainMappable<R>, R> Single<List<T>>.mapListToDomain(): Single<List<R>> = this.map { it.map { t -> t.asDomain() } }
