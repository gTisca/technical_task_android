/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.presentation.base

/**
 * Interface implemented in all activities and fragments
 * used to show an error
 */
interface BaseErrorHandlingView {

    fun onError(throwable: Throwable)
}