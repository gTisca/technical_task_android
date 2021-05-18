/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tmg.slide.R
import com.tmg.slide.domain.models.UserModel

class MainListAdapter(private val userClickListener: (UserModel) -> Unit) : ListAdapter<UserModel, MainListAdapter.UsersViewHolder>(UserListItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UsersViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val userName = view.findViewById<TextView>(R.id.userName_tv)
        private val userEmail = view.findViewById<TextView>(R.id.userEmail_tv)
        private val userCreationDate = view.findViewById<TextView>(R.id.userCreationDate_tv)

        internal fun bind(userModel: UserModel) {
            view.setOnLongClickListener {
                userClickListener.invoke(userModel)
                true
            }
            userName.text = userModel.name
            userEmail.text = userModel.email
            userCreationDate.text = userModel.creationDate
        }
    }
}

class UserListItemDiffCallBack : DiffUtil.ItemCallback<UserModel>() {
    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.id == newItem.id
    }
}