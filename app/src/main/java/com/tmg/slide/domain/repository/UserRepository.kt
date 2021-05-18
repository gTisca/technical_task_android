/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.domain.repository

import com.tmg.slide.domain.models.CreateUserModel
import com.tmg.slide.domain.models.UserModel
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {

    /**
     * Method used to get a list of last added users
     */
    fun getLastUsers(): Single<List<UserModel>>

    /**
     * Method used to create a new user
     */
    fun createUser(createUserModel: CreateUserModel): Single<UserModel>

    /**
     * Method used to deleted a user by id
     */
    fun deleteUser(id: Int): Completable
}