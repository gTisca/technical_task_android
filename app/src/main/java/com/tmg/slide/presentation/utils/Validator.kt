/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.presentation.utils

import android.util.Patterns

fun validateField(field: String?): Boolean {
    return field?.isNotEmpty() ?: false
}

fun validateEmail(email: String?): Boolean {
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email ?: "").matches()
}