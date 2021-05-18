/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.data.models

data class CreateUserRequestModel(
    val name: String,
    val email: String,
    val gender: String = "Female",
    val status: String = "Active"
)