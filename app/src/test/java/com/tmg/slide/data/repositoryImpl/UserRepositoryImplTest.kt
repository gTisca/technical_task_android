/*
* // Project SlideTest.
* //
* // Created by TMG on 18/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.data.repositoryImpl

import com.tmg.slide.data.models.*
import com.tmg.slide.data.network.api.DataApi
import com.tmg.slide.data.utils.SlideGson
import com.tmg.slide.domain.models.AppErrors
import com.tmg.slide.domain.models.CreateUserModel
import com.tmg.slide.utils.BaseTest
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import java.net.HttpURLConnection
import java.util.*

class UserRepositoryImplTest : BaseTest() {

    private lateinit var userRepositoryImpl: UserRepositoryImpl

    @Mock
    lateinit var dataApi: DataApi

    @Before
    fun setUp() {
        userRepositoryImpl = UserRepositoryImpl(dataApi)
    }

    @Test
    fun `when getUsers server returns code != 200 then throw error`() {
        //given
        val paginationMeta = PaginationMeta(Pagination(1, 1, 1, 1))
        `when`(dataApi.getUsers(1)).thenReturn(Single.just(BaseResponseModel(HttpURLConnection.HTTP_BAD_REQUEST, paginationMeta, listOf())))

        //when
        val testObserver = userRepositoryImpl.getLastUsers().test()

        //then
        testObserver.assertError(AppErrors.GeneralError)
    }

    @Test
    fun `when getUsers server returns code == 200 then show list of users`() {
        //given
        val paginationMeta = PaginationMeta(Pagination(1, 1, 1, 1))
        `when`(dataApi.getUsers(1)).thenReturn(Single.just(BaseResponseModel(HttpURLConnection.HTTP_OK, paginationMeta, listOf())))

        //when
        val testObserver = userRepositoryImpl.getLastUsers().test()

        //then
        testObserver.assertResult(listOf())
    }

    @Test
    fun `when delete user server returns code == 204 then complete`() {
        //given
        val userID = 1
        `when`(dataApi.deleteUser(userID)).thenReturn(Single.just(DeleteUserResponse(HttpURLConnection.HTTP_NO_CONTENT)))

        //when
        val testObserver = userRepositoryImpl.deleteUser(userID).test()

        //then
        testObserver.assertComplete()
    }

    @Test
    fun `when delete user server returns code != 204 then throw error`() {
        //given
        val userID = 1
        `when`(dataApi.deleteUser(userID)).thenReturn(Single.just(DeleteUserResponse(HttpURLConnection.HTTP_BAD_GATEWAY)))

        //when
        val testObserver = userRepositoryImpl.deleteUser(userID).test()

        //then
        testObserver.assertError(AppErrors.UserDeleteError)
    }

    @Test
    fun `when create user server returns code != 202 then throw error`() {
        //given
        val user = CreateUserRequestModel("John", "john@yahoo.com")
        val returnedError = listOf(ErrorModel("email", "already taken"))
        `when`(dataApi.createUser(user)).thenReturn(
            Single.just(BaseResponseModel(HttpURLConnection.HTTP_BAD_GATEWAY, Unit, SlideGson.gsonInstance.toJsonTree(returnedError)))
        )

        //when
        val testObserver = userRepositoryImpl.createUser(CreateUserModel(user.name, user.email)).test()

        //then
        testObserver.assertError(AppErrors.HttpCallFailureException::class.java)
    }

    @Test
    fun `when create user server returns code == 202 then return created user`() {
        //given
        val user = CreateUserRequestModel("John", "john@yahoo.com")
        val createdUser = UserModel(1, user.name, user.email, Date())
        `when`(dataApi.createUser(user)).thenReturn(Single.just(BaseResponseModel(HttpURLConnection.HTTP_CREATED, Unit, SlideGson.gsonInstance.toJsonTree(createdUser))))

        //when
        val testObserver = userRepositoryImpl.createUser(CreateUserModel(user.name, user.email)).test()

        //then
        testObserver.assertValue { it.id == createdUser.id }
    }
}