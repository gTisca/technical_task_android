/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.data.models

import com.google.gson.annotations.SerializedName
import com.tmg.slide.data.utils.DomainMappable
import com.tmg.slide.domain.models.UserModel
import java.util.*

data class UserModel(
    val id: Int,
    val name: String,
    val email: String,
    @SerializedName("created_at")
    val creationDate: Date
) : DomainMappable<UserModel> {

    override fun asDomain(): UserModel {
        val diff = (Date().time - creationDate.time) / 1000 / 60

        return UserModel(
            id, name, email,
            String.format("%02d:%02d ago", diff / 60, diff % 60)
        )
    }
}

data class PaginationMeta(
    @SerializedName("pagination")
    val pagination: Pagination
)

data class Pagination(
    val total: Int,
    val pages: Int,
    val page: Int,
    val limit: Int
)