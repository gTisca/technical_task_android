/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/
package com.tmg.slide.presentation.base

/**
 * Interface used to get the presenter for each class implementing it
 *
 * @param <T>
 */
interface BaseView<T> {

    fun getPresenter(): T
}