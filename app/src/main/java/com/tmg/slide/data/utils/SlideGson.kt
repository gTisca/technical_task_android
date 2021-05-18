/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.data.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.text.DateFormat

object SlideGson {

    val gsonInstance: Gson = GsonBuilder()
        .setDateFormat(DateFormat.FULL, DateFormat.FULL)
        .setLenient()
        .create()
}