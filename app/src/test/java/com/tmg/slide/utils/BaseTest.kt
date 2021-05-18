/*
 * //
 * // Description: IWoodTracking
 * //
 * // 4/15/2020
 * //
 * // Copyright (c) Rosoftlab Romania. All rights reserved.
 * //
 */
package com.tmg.slide.utils

import org.junit.Rule
import org.mockito.junit.MockitoJUnit

open class BaseTest {

    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()
}