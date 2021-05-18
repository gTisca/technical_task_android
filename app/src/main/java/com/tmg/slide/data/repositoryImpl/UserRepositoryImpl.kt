/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.data.repositoryImpl

import com.tmg.slide.data.models.CreateUserRequestModel
import com.tmg.slide.data.models.ErrorModel
import com.tmg.slide.data.network.api.DataApi
import com.tmg.slide.data.utils.SlideGson
import com.tmg.slide.data.utils.mapErrors
import com.tmg.slide.data.utils.mapListToDomain
import com.tmg.slide.data.utils.mapToDomain
import com.tmg.slide.domain.models.AppErrors
import com.tmg.slide.domain.models.CreateUserModel
import com.tmg.slide.domain.models.UserModel
import com.tmg.slide.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.net.HttpURLConnection

class UserRepositoryImpl(private val dataApi: DataApi) : UserRepository {

    override fun getLastUsers(): Single<List<UserModel>> {
        return dataApi.getUsers()
            .flatMap {
                if (it.code != HttpURLConnection.HTTP_OK) {
                    Single.error(AppErrors.GeneralError)
                } else {
                    dataApi.getUsers(it.metaData.pagination.pages)
                        .flatMap { lastResponse ->
                            if (lastResponse.code != HttpURLConnection.HTTP_OK) {
                                Single.error(AppErrors.GeneralError)
                            } else {
                                Single.just(lastResponse.data)
                            }
                        }
                }
            }
            .mapListToDomain()
            .mapErrors()
    }

    override fun createUser(createUserModel: CreateUserModel): Single<UserModel> {
        return dataApi.createUser(CreateUserRequestModel(createUserModel.name, createUserModel.email))
            .flatMap { response ->
                if (response.code != HttpURLConnection.HTTP_CREATED) {
                    val errors = SlideGson.gsonInstance.fromJson(response.data.toString(), Array<ErrorModel>::class.java).toList()
                    Single.error(AppErrors.HttpCallFailureException(errors.joinToString("/n", transform = { it.field.plus(" ${it.message}") })))
                } else {
                    Single.just(SlideGson.gsonInstance.fromJson(response.data.toString(), com.tmg.slide.data.models.UserModel::class.java))
                }
            }
            .mapToDomain()
            .mapErrors()
    }

    override fun deleteUser(id: Int): Completable {
        return dataApi.deleteUser(id)
            .flatMapCompletable {
                if (it.code != HttpURLConnection.HTTP_NO_CONTENT) {
                    Completable.error(AppErrors.UserDeleteError)
                } else {
                    Completable.fromCallable { Completable.complete() }
                }
            }
            .mapErrors()
    }
}