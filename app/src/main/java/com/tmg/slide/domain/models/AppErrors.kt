package com.tmg.slide.domain.models

sealed class AppErrors : Exception() {
    object NoNetworkException : AppErrors()
    object ServerUnreachableException : AppErrors()
    object GeneralError : AppErrors()
    object UserDeleteError : AppErrors()
    class HttpCallFailureException(val errorMessage: String?) : AppErrors()
}