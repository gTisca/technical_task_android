/*
 * //
 * // Description: IWoodTracking
 * //
 * // 3/18/2020
 * //
 * // Copyright (c) Rosoftlab Romania. All rights reserved.
 * //
 */
package com.tmg.slide.domain.models

sealed class AppErrors : Exception() {
    object NoNetworkException : AppErrors()
    object ServerUnreachableException : AppErrors()
    object GeneralError : AppErrors()
    object UserCreationError : AppErrors()
    object UserDeleteError : AppErrors()
    class HttpCallFailureException(val errorMessage: String?) : AppErrors()
}