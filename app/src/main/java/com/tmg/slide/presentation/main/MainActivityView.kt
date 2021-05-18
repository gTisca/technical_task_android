/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.presentation.main

import com.tmg.slide.domain.models.UserModel
import com.tmg.slide.presentation.base.BaseErrorHandlingView

interface MainActivityView : BaseErrorHandlingView {

    fun showUsers(users: List<UserModel>)
    fun showLoading(isVisible: Boolean)
    fun showEmptyScreen()
    fun showDeleteConfirmationDialog(userModel: UserModel)
    fun showAddDialog()
}