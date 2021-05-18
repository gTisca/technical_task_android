/*
* // Project SlideTest.
* //
* // Created by TMG on 18/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.presentation.main

import com.tmg.slide.domain.models.CreateUserModel
import com.tmg.slide.domain.models.UserModel
import com.tmg.slide.domain.usecase.UserUseCase
import com.tmg.slide.utils.BaseTest
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito.`when`

class MainActivityPresenterTest : BaseTest() {

    private lateinit var presenter: MainActivityPresenter

    @Mock
    lateinit var userUseCase: UserUseCase

    @Mock
    lateinit var mainActivityView: MainActivityView

    @Before
    fun setUp() {
        presenter = MainActivityPresenter(userUseCase)
    }

    @Test
    fun `when activity starts first time then get users from server`() {
        //Given
        `when`(userUseCase.getLastUsers()).thenReturn(Single.just(listOf()))

        //When
        presenter.bind(mainActivityView)

        //then
        then(userUseCase).should().getLastUsers()
    }

    @Test
    fun `when user list is empty then show empty screen`() {
        //Given
        `when`(userUseCase.getLastUsers()).thenReturn(Single.just(listOf()))

        //When
        presenter.bind(mainActivityView)

        //then
        then(mainActivityView).should().showEmptyScreen()
    }

    @Test
    fun `when user list is not empty then show list of users`() {
        //Given
        val usersList = listOf(UserModel(1, "John", "jaohn@hhh.com", "01.02.2021"))
        `when`(userUseCase.getLastUsers()).thenReturn(Single.just(usersList))

        //When
        presenter.bind(mainActivityView)

        //then
        then(mainActivityView).should().showUsers(usersList)
    }

    @Test
    fun `when activity starts and there is a list of users then not update it again`() {
        //Given
        presenter.users = listOf(UserModel(1, "John", "jaohn@hhh.com", "01.02.2021"))

        //When
        presenter.bind(mainActivityView)

        //then
        then(userUseCase).shouldHaveZeroInteractions()
    }

    @Test
    fun `when new user is added then update list of users`() {
        //Given
        val returnUser = UserModel(1, "John", "Ion@jjj.com", "01.02.2021")
        presenter.users = listOf(returnUser)
        presenter.bind(mainActivityView)
        `when`(userUseCase.addNewUser(CreateUserModel(returnUser.name, returnUser.email))).thenReturn(Single.just(returnUser))

        //When
        presenter.onUserAdded(returnUser.name, returnUser.email)

        //then
        then(userUseCase).should().getLastUsers()
    }

    @Test
    fun `when user is deleted then update list of users`() {
        //Given
        val returnUser = UserModel(1, "John", "Ion@jjj.com", "01.02.2021")
        presenter.users = listOf(returnUser)
        presenter.bind(mainActivityView)
        `when`(userUseCase.getLastUsers()).thenReturn(Single.just(listOf()))
        `when`(userUseCase.deleteUserById(returnUser.id)).thenReturn(Completable.fromCallable { Completable.complete() })

        //When
        presenter.onDeleteDialogConfirmed(returnUser)

        //then
        then(userUseCase).should().getLastUsers()
    }
}