/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.domain.usecase

import com.tmg.slide.domain.models.CreateUserModel
import com.tmg.slide.domain.models.UserModel
import com.tmg.slide.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Single

class UserUseCase(private val userRepository: UserRepository) {

    internal fun getLastUsers(): Single<List<UserModel>> {
        return userRepository.getLastUsers()
    }

    internal fun addNewUser(createUserModel: CreateUserModel): Single<UserModel> {
        return userRepository.createUser(createUserModel)
    }

    internal fun deleteUserById(userID: Int): Completable {
        return userRepository.deleteUser(userID)
    }
}