/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.presentation.main

import com.tmg.slide.domain.models.CreateUserModel
import com.tmg.slide.domain.models.UserModel
import com.tmg.slide.domain.usecase.UserUseCase
import com.tmg.slide.presentation.base.AbstractBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainActivityPresenter(private val userUseCase: UserUseCase) : AbstractBasePresenter<MainActivityView>() {

    internal var users: List<UserModel> = listOf()

    override fun afterBind() {
        if (users.isNotEmpty())
            return

        getUsers()
    }

    internal fun getUsers() {
        addDisposable(
            userUseCase.getLastUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { getView().showLoading(true) }
                .doAfterTerminate { getView().showLoading(false) }
                .doOnSuccess { users = it }
                .subscribeBy(
                    onSuccess = { showUsers(it) },
                    onError = { getView().onError(it) }
                )
        )
    }

    internal fun onUserClicked(userModel: UserModel) {
        getView().showDeleteConfirmationDialog(userModel)
    }

    internal fun onDeleteDialogConfirmed(userModel: UserModel) {
        addDisposable(
            userUseCase.deleteUserById(userModel.id)
                .andThen(userUseCase.getLastUsers())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { getView().showLoading(true) }
                .doAfterTerminate { getView().showLoading(false) }
                .doOnSuccess { users = it }
                .subscribeBy(
                    onSuccess = { showUsers(users) },
                    onError = { getView().onError(it) }
                )
        )
    }

    internal fun addUserBtnClicked() {
        getView().showAddDialog()
    }

    internal fun onUserAdded(name: String, email: String) {
        addDisposable(
            userUseCase.addNewUser(CreateUserModel(name, email))
                .flatMap { userUseCase.getLastUsers() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { getView().showLoading(true) }
                .doAfterTerminate { getView().showLoading(false) }
                .doOnSuccess { users = it }
                .subscribeBy(
                    onSuccess = { showUsers(users) },
                    onError = { getView().onError(it) }
                )
        )
    }

    private fun showUsers(users: List<UserModel>) {
        if (users.isEmpty()) {
            getView().showEmptyScreen()
        } else {
            getView().showUsers(users)
        }
    }
}
