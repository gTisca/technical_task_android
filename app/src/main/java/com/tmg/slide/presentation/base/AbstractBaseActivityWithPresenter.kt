/*
* // Project SlideTest.
* //
* // Created by TMG on 17/05/2021.
* //
* // Copyright (c) TMG Studio. All rights reserved.
*/package com.tmg.slide.presentation.base

import androidx.appcompat.app.AppCompatActivity

@Suppress("UNCHECKED_CAST")
abstract class AbstractBaseActivityWithPresenter<V : BaseErrorHandlingView, T : AbstractBasePresenter<V>> : AppCompatActivity(), BaseView<T>, BaseErrorHandlingView {

    override fun onStart() {
        super.onStart()
        getPresenter().bind(this as V)
    }

    override fun onError(throwable: Throwable) {
        BaseErrorHandling.handleError(this, throwable)
    }

    override fun onStop() {
        getPresenter().unbind()
        super.onStop()
    }
}