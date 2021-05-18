/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.presentation.base

import android.app.Activity
import com.afollestad.materialdialogs.MaterialDialog
import com.tmg.slide.R
import com.tmg.slide.domain.models.AppErrors
import timber.log.Timber

object BaseErrorHandling {

    fun handleError(activity: Activity, throwable: Throwable) {
        Timber.e(throwable)

        val error: String = when (throwable) {
            is AppErrors.NoNetworkException -> activity.getString(R.string.error_no_internet_connection_message)
            is AppErrors.ServerUnreachableException -> activity.getString(R.string.error_server_not_reachable_message)
            is AppErrors.HttpCallFailureException -> throwable.errorMessage ?: activity.getString(R.string.error_unknown_exception_message)
            is AppErrors.GeneralError -> activity.getString(R.string.error_unknown_exception_message)
            is AppErrors.UserDeleteError -> activity.getString(R.string.error_delete_user)
            else -> activity.getString(R.string.error_unknown_exception_message)
        }

        showErrorDialog(activity, error)
    }

    /**
     * Used to show a general error dialog message
     */
    private fun showErrorDialog(activity: Activity, errorMsg: String) {
        if (activity.isFinishing || activity.isDestroyed) {
            return
        }

        MaterialDialog(activity).show {
            title(text = activity.getString(R.string.dialog_title))
            message(text = errorMsg)
            positiveButton(R.string.error_dialog_ok_button)
        }
    }
}