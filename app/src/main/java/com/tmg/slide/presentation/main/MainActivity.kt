/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.presentation.main

import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tmg.slide.R
import com.tmg.slide.domain.models.UserModel
import com.tmg.slide.presentation.base.AbstractBaseActivityWithPresenter
import com.tmg.slide.presentation.utils.setVisibility
import com.tmg.slide.presentation.utils.validateEmail
import com.tmg.slide.presentation.utils.validateField
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.circular_progress_bar.*
import org.koin.android.ext.android.inject

class MainActivity : AbstractBaseActivityWithPresenter<MainActivityView, MainActivityPresenter>(), MainActivityView {

    private val mainActivityPresenter: MainActivityPresenter by inject()
    private lateinit var usersListAdapter: MainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupList()
        setListeners()
    }

    private fun setupList() {
        usersListAdapter = MainListAdapter(mainActivityPresenter::onUserClicked)
        usersList_rv.adapter = usersListAdapter
    }

    private fun setListeners() {
        refreshList_sr.setOnRefreshListener {
            mainActivityPresenter.getUsers()
            refreshList_sr.isRefreshing = false
        }
        addUser_btn.setOnClickListener { mainActivityPresenter.addUserBtnClicked() }
    }

    override fun showUsers(users: List<UserModel>) {
        usersListAdapter.submitList(users)
        emptyView_ll.setVisibility(false)
    }

    override fun showLoading(isVisible: Boolean) {
        loading_ll.setVisibility(isVisible)
    }

    override fun showDeleteConfirmationDialog(userModel: UserModel) {
        MaterialDialog(this).show {
            title(R.string.dialog_title)
            message(text = getString(R.string.dialog_delete_confirmation_msg, userModel.name))
            positiveButton(R.string.dialog_delete_positive_btn) { mainActivityPresenter.onDeleteDialogConfirmed(userModel) }
            negativeButton(R.string.dialog_delete_negative_btn)
        }
    }

    override fun showAddDialog() {
        val a = MaterialDialog(this).show {
            title(R.string.dialog_add_title)
            customView(R.layout.add_user_dialog, scrollable = true, horizontalPadding = true)
            noAutoDismiss()

            val name = getCustomView().findViewById<TextInputEditText>(R.id.name_et)
            val nameTil = getCustomView().findViewById<TextInputLayout>(R.id.name_til)
            val email = getCustomView().findViewById<TextInputEditText>(R.id.email_et)
            val emailTil = getCustomView().findViewById<TextInputLayout>(R.id.email_til)

            positiveButton(R.string.dialog_add_positive_btn) {
                nameTil.error = null
                emailTil.error = null

                if (!validateField(name.text?.toString())) {
                    nameTil.error = getString(R.string.dialog_add_empty_name_error)
                    return@positiveButton
                }

                if (!validateEmail(email.text?.toString())) {
                    emailTil.error = getString(R.string.dialog_add_empty_email_error)
                    return@positiveButton
                }

                mainActivityPresenter.onUserAdded(name.text.toString(), email.text.toString())
                dismiss()
            }
            negativeButton(R.string.dialog_add_negative_btn) { dismiss() }
        }
    }

    override fun showEmptyScreen() {
        usersList_rv.setVisibility(false)
        emptyView_ll.setVisibility(true)
    }

    override fun getPresenter() = mainActivityPresenter
}