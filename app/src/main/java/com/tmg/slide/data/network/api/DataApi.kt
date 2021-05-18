/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.data.network.api

import com.google.gson.JsonElement
import com.tmg.slide.data.models.*
import io.reactivex.Single
import retrofit2.http.*

interface DataApi {

    @GET("users")
    fun getUsers(@Query("page") page: Int = 1): Single<BaseResponseModel<PaginationMeta, List<UserModel>>>

    @POST("users")
    fun createUser(@Body body: CreateUserRequestModel): Single<BaseResponseModel<Unit, JsonElement>>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: Int): Single<DeleteUserResponse>
}