/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.data.models

import com.google.gson.annotations.SerializedName

data class BaseResponseModel<M, D>(
    val code: Int,
    @SerializedName("meta")
    val metaData: M,
    @SerializedName("data")
    val data: D
)